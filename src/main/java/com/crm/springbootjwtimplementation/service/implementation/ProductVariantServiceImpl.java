package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Product;
import com.crm.springbootjwtimplementation.domain.ProductVariant;
import com.crm.springbootjwtimplementation.domain.ProductVariantImage;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductVariantDTO;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductVariantImageDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.mapper.ProductVariantMapper;
import com.crm.springbootjwtimplementation.repository.ProductRepository;
import com.crm.springbootjwtimplementation.repository.ProductVariantRepository;
import com.crm.springbootjwtimplementation.service.ProductVariantService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductRepository productRepo;
    private final ProductVariantRepository variantRepo;
    private final ProductVariantMapper mapper;
    private final CloudinaryImageUploadService uploadService;

    @Override
    @Transactional
    public ProductVariantDTO addVariant(Long productId,
            ProductVariantDTO dto,
            List<MultipartFile> images) {

        // SKU uniqueness
        if (variantRepo.existsBySku(dto.getSku())) {
            throw new CustomSecurityException(
                    ApiMessages.VARIANT_SKU_EXISTS + dto.getSku(),
                    HttpStatus.CONFLICT);
        }

        // parent product must exist
        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.PRODUCT_NOT_FOUND + productId,
                        HttpStatus.NOT_FOUND));

        // map + attach
        ProductVariant variant = mapper.toEntity(dto);
        variant.setProduct(product);

        // save base variant
        variant = variantRepo.save(variant);

        // handle image uploads
        if (images != null) {
            for (MultipartFile file : images) {
                String url = uploadService.upload(file);
                ProductVariantImage img = new ProductVariantImage();
                img.setImageUrl(url);
                img.setProductVariant(variant);
                variant.getImages().add(img);
            }
            variant = variantRepo.save(variant);
        }

        // map out + attach URLs
        ProductVariantDTO out = mapper.toDto(variant);
  if (!variant.getImages().isEmpty()) {
            List<ProductVariantImageDTO> pics = variant.getImages().stream()
                .map(image -> {
                    ProductVariantImageDTO picDto = new ProductVariantImageDTO();
                    picDto.setId(image.getId());
                    picDto.setImageUrl(image.getImageUrl());
                    return picDto;
                })
                .collect(Collectors.toList());
            out.setImageUrls(pics);
        }        return out;
    }

    @Override
    @Transactional
    public ProductVariantDTO updateVariant(Long id,
            ProductVariantDTO dto,
            MultipartFile[] newImages) {

        // 1) Fetch existing or 404
        ProductVariant existing = variantRepo.findById(id)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.VARIANT_NOT_FOUND + id,
                        HttpStatus.NOT_FOUND));

        // 2) Copy non-null fields from DTO
        mapper.updateFromDto(dto, existing);

        // 3) Handle any new images via our shared ImageUploadService
        if (newImages != null && newImages.length > 0) {
            for (MultipartFile file : newImages) {
                if (file.isEmpty()) {
                    continue;
                }
                // delegate upload & get URL
                String url = uploadService.upload(file);

                // associate uploaded image
                ProductVariantImage img = new ProductVariantImage();
                img.setImageUrl(url);
                img.setProductVariant(existing);
                existing.getImages().add(img);
            }
        }

        // 4) Persist all changes in one save
        ProductVariant saved = variantRepo.save(existing);

        // 5) Map back to DTO (including all image URLs)
        ProductVariantDTO out = mapper.toDto(saved);
       if (!saved.getImages().isEmpty()) {
            List<ProductVariantImageDTO> pics = saved.getImages().stream()
                .map(image -> {
                    ProductVariantImageDTO picDto = new ProductVariantImageDTO();
                    picDto.setId(image.getId());
                    picDto.setImageUrl(image.getImageUrl());
                    return picDto;
                })
                .collect(Collectors.toList());
            out.setImageUrls(pics);
        }
        return out;
    }
@Override
@Transactional
public boolean deleteVariant(Long id) {
    if (!variantRepo.existsById(id)) {
        throw new CustomSecurityException(
            ApiMessages.VARIANT_NOT_FOUND + id,
            HttpStatus.NOT_FOUND
        );
    }

    try {
        // 1) Delete the row
        variantRepo.deleteById(id);
        // 2) Force Hibernate/JPA to execute the SQL now
        variantRepo.flush();

        return true;
    } catch (org.springframework.dao.DataIntegrityViolationException ex) {
        // FK constraint failed (e.g. cart_item → product_variant)
        throw new CustomSecurityException(
            "Failed to delete variant; it’s still referenced by other records.",
            HttpStatus.CONFLICT
        );
    } catch (Exception ex) {
        throw new CustomSecurityException(
            "Failed to delete variant: " + ex.getMessage(),
            HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
}
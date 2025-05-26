package com.crm.springbootjwtimplementation.service.implementation;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.crm.springbootjwtimplementation.domain.Product;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductDTO;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductSummaryDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.mapper.ProductMapper;
import com.crm.springbootjwtimplementation.repository.ProductRepository;
import com.crm.springbootjwtimplementation.repository.ProductVariantRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements com.crm.springbootjwtimplementation.service.ProductService {

    private final ProductRepository productRepo;
    private final ProductVariantRepository variantRepo;
    private final UserRepository userRepo;
    private final ProductMapper mapper;
    private final CloudinaryImageUploadService imageService;

    @Override
    @Transactional
    public ProductDTO createProduct(Long userId, ProductDTO dto,
            MultipartFile thumbnail) {
        // 1) ensure each SKU is unique
        if (dto.getVariants() != null) {
            dto.getVariants().forEach(v -> {
                if (variantRepo.existsBySku(v.getSku())) {
                    throw new CustomSecurityException(
                            "Variant SKU already exists: " + v.getSku(),
                            HttpStatus.CONFLICT);
                }
            });
        }

        // 2) verify user exists
        userRepo.findById(userId)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.USER_NOT_FOUND + userId,
                        HttpStatus.NOT_FOUND));

        // 3) map + set creation date
        var entity = mapper.toEntity(dto);
        entity.setCreatedDate(LocalDate.now());
        if (thumbnail != null && !thumbnail.isEmpty()) {
            entity.setThumbnailUrl(imageService.upload(thumbnail));
        }
        // 4) ensure bi-directional link
        if (entity.getVariants() != null) {
            entity.getVariants().forEach(variant -> variant.setProduct(entity));
        }

        // 5) save + return DTO
        var saved = productRepo.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> listProducts(Pageable pageable) {
        if (pageable.getPageSize() <= 0 || pageable.getPageNumber() < 0) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_INPUT_DATA,
                    HttpStatus.BAD_REQUEST);
        }
        return productRepo.findAll(pageable)
                .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        var product = productRepo.findById(id)
                .orElseThrow(() -> new CustomSecurityException(
                        "Product not found with ID: " + id,
                        HttpStatus.NOT_FOUND));
        return mapper.toDto(product);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id,
            ProductDTO dto,
            MultipartFile thumbnail) {
        // 1) load existing
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.PRODUCT_NOT_FOUND + id,
                        HttpStatus.NOT_FOUND));

        // 2) apply any non-null fields from DTO
        mapper.updateFromDto(dto, existing);

        // 3) if a new thumbnail is provided, upload & set it;
        // if explicitly null (empty), clear it
        if (thumbnail != null) {
            if (!thumbnail.isEmpty()) {
                String url = imageService.upload(thumbnail);
                existing.setThumbnailUrl(url);
            } else {
                // user wants to remove thumbnail
                existing.setThumbnailUrl(null);
            }
        }

        // 4) persist & return
        Product saved = productRepo.save(existing);
        return mapper.toDto(saved);
    }
  @Override
  @Transactional
  public ProductDTO updateThumbnail(Long id, MultipartFile thumbnail) {
    if (thumbnail == null || thumbnail.isEmpty()) {
      throw new CustomSecurityException(
        ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
    }

    Product existing = productRepo.findById(id)
      .orElseThrow(() -> new CustomSecurityException(
        ApiMessages.PRODUCT_NOT_FOUND + id,
        HttpStatus.NOT_FOUND));

    // Upload and set new thumbnail URL
    String url = imageService.upload(thumbnail);
    existing.setThumbnailUrl(url);

    Product saved = productRepo.save(existing);
    return mapper.toDto(saved);
  }


    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepo.existsById(id)) {
            throw new CustomSecurityException(
                    "Product not found with ID: " + id,
                    HttpStatus.NOT_FOUND);
        }
        productRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductDTO> searchProducts(String name, Pageable pageable) {
        if (name == null || name.isBlank()) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_INPUT_DATA,
                    HttpStatus.BAD_REQUEST);
        }
        if (pageable.getPageSize() <= 0 || pageable.getPageNumber() < 0) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_INPUT_DATA,
                    HttpStatus.BAD_REQUEST);
        }
        return productRepo
                .findByProductNameContainingIgnoreCase(name, pageable)
                .map(mapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductSummaryDTO> listSummaries(Pageable pageable) {
        if (pageable.getPageSize() <= 0 || pageable.getPageNumber() < 0) {
            throw new CustomSecurityException(
                    ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }
        return productRepo.findAll(pageable)
                .map(mapper::toSummaryDto);
    }

    @Override
    @Transactional
    public void setActive(Long id, boolean active) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new CustomSecurityException(
                        ApiMessages.INVALID_ID + id, HttpStatus.NOT_FOUND));
        product.setProductStatus(active ? "ACTIVE" : "INACTIVE");
        Product saved = productRepo.save(product);
        // return mapper.toDto(saved);
    }
}

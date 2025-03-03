package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Product;
import com.crm.springbootjwtimplementation.domain.ProductVariant;
import com.crm.springbootjwtimplementation.domain.ProductVariantImage;
import com.crm.springbootjwtimplementation.domain.dto.ProductVariantDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.ProductRepository;
import com.crm.springbootjwtimplementation.repository.ProductVariantRepository;
import com.crm.springbootjwtimplementation.service.ProductVariantService;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductVariantServiceImpl implements ProductVariantService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ProductVariantDTO addVariant(Long productId, ProductVariantDTO productVariantDTO,
            List<MultipartFile> images) {
        // Check for SKU uniqueness.
        if (productVariantRepository.existsBySku(productVariantDTO.getSku())) {
            throw new CustomSecurityException("SKU already exists", HttpStatus.CONFLICT);
        }

        // Find the parent product.
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new CustomSecurityException("Product not found", HttpStatus.NOT_FOUND));

        // Map the DTO to the entity.
        ProductVariant variant = new ProductVariant();
        variant.setVariantName(productVariantDTO.getVariantName());
        variant.setPrice(productVariantDTO.getPrice());
        variant.setSku(productVariantDTO.getSku());
        variant.setUnits(productVariantDTO.getUnits());        
        variant.setProduct(product);

        // Save variant to generate an ID and trigger prePersist.
        variant = productVariantRepository.save(variant);

        // Process and attach images if provided using Cloudinary.
        for (MultipartFile file : images) {
            try {
                Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("secure_url");
                System.out.println("Uploaded image URL: " + imageUrl);

                ProductVariantImage variantImage = new ProductVariantImage();
                variantImage.setImageUrl(imageUrl);
                variantImage.setProductVariant(variant);
                variant.getImages().add(variantImage);
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }
        
        // Save the variant again after adding images.
        variant = productVariantRepository.save(variant);

        // Map back to DTO.
        ProductVariantDTO resultDTO = modelMapper.map(variant, ProductVariantDTO.class);
        // Optionally, manually set image URLs:
        if (variant.getImages() != null) {
            resultDTO.setImageUrls(
                    variant.getImages()
                           .stream()
                           .map(ProductVariantImage::getImageUrl)
                           .collect(Collectors.toList())
            );
        }
        return resultDTO;
    }

    @Override
    public boolean deleteVariant(Long variantId) {
        // Check if the variant exists.
        Optional<ProductVariant> variantOpt = productVariantRepository.findById(variantId);
        if (!variantOpt.isPresent()) {
            throw new CustomSecurityException("Product variant not found with ID: " + variantId, HttpStatus.NOT_FOUND);
        }
        // Delete the variant.
        productVariantRepository.deleteById(variantId);
        return true;
    }

    @Override
    public ProductVariantDTO updateVariant(Long variantId, ProductVariantDTO variantDTO, MultipartFile[] newImages) {
        // Fetch the existing variant.
        ProductVariant existingVariant = productVariantRepository.findById(variantId)
                .orElseThrow(() -> new CustomSecurityException("Product variant not found with ID: " + variantId, HttpStatus.NOT_FOUND));
    
        // Update variant fields (skipping null properties).
        BeanUtils.copyProperties(variantDTO, existingVariant, getNullPropertyNames(variantDTO));
    
        System.out.println("updateVariant: newImages length: " + (newImages == null ? 0 : newImages.length));
    
        // Process new images if provided.
        if (newImages != null && newImages.length > 0) {
            for (MultipartFile file : newImages) {
                if (!file.isEmpty()) {
                    try {
                        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                        String imageUrl = (String) uploadResult.get("secure_url");
                        System.out.println("Uploaded image URL: " + imageUrl);
    
                        ProductVariantImage variantImage = new ProductVariantImage();
                        variantImage.setImageUrl(imageUrl);
                        variantImage.setProductVariant(existingVariant);
                        existingVariant.getImages().add(variantImage);
                    } catch (IOException e) {
                        throw new RuntimeException("Image upload failed", e);
                    }
                } else {
                    System.out.println("Encountered an empty file; skipping.");
                }
            }
        }
    
        // Save the updated variant.
        ProductVariant updatedVariant = productVariantRepository.save(existingVariant);
    
        // Map the updated variant back to its DTO.
        ProductVariantDTO updatedVariantDTO = modelMapper.map(updatedVariant, ProductVariantDTO.class);
        if (updatedVariant.getImages() != null) {
            updatedVariantDTO.setImageUrls(
                    updatedVariant.getImages().stream()
                                  .map(ProductVariantImage::getImageUrl)
                                  .collect(Collectors.toList())
            );
        }
        return updatedVariantDTO;
    }
    
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {
            Object value = src.getPropertyValue(descriptor.getName());
            if (value == null) {
                emptyNames.add(descriptor.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
}

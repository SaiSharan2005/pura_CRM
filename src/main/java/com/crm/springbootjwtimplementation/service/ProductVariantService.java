package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.ProductVariantDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface ProductVariantService {
    // New method signature that includes images.
    ProductVariantDTO addVariant(Long productId, ProductVariantDTO productVariantDTO, List<MultipartFile> images);
    ProductVariantDTO updateVariant(Long variantId, ProductVariantDTO variantDTO, MultipartFile[] newImages);
    boolean deleteVariant(Long variantId);

}

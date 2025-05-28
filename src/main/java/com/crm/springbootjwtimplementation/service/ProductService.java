package com.crm.springbootjwtimplementation.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.crm.springbootjwtimplementation.dto.product.ProductDTO;
import com.crm.springbootjwtimplementation.dto.product.ProductSummaryDTO;

public interface ProductService {
    ProductDTO createProduct(Long userId, ProductDTO dto, MultipartFile thumbnail);

    Page<ProductDTO> listProducts(Pageable pageable);

    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, ProductDTO dto, MultipartFile thumbnail);

    void deleteProduct(Long id);

    // extra
    Page<ProductDTO> searchProducts(String name, Pageable pageable);

    Page<ProductSummaryDTO> listSummaries(Pageable pageable);

    void setActive(Long id, boolean active);

    ProductDTO updateThumbnail(Long id, MultipartFile thumbnail);

}

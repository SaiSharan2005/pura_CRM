package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.ProductDTO;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ProductService {
    ProductDTO createProduct(Long userId, ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO getProductById(Long id);
    ProductDTO updateProductDetails(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}

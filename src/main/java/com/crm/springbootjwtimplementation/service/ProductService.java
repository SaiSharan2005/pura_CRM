package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    ProductDTO createProduct(Long id , ProductDTO productDTO);

    List<ProductDTO> getAllProducts();

    ProductDTO getProductById(Long id);

    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    void deleteProduct(Long id);
}

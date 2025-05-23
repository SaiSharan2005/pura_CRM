package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.dto.ProductDTO;
import com.crm.springbootjwtimplementation.domain.dto.ProductVariantDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.ProductService;
import com.crm.springbootjwtimplementation.service.ProductVariantService;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        return ResponseEntity.ok(productService.createProduct(userToken.getId(), productDTO));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping(value = "/{id}/details", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> updateProductDetails(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.updateProductDetails(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @PutMapping(value = "/variants/{variantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductVariantDTO> updateVariant(
            @PathVariable Long variantId,
            @RequestPart("variant") String variantJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {
        try {
            System.out.println("Received variant JSON: " + variantJson);
            System.out.println("Received " + (images != null ? images.length : 0) + " images");
    
            ObjectMapper mapper = new ObjectMapper();
            ProductVariantDTO variantDTO = mapper.readValue(variantJson, ProductVariantDTO.class);
    
            ProductVariantDTO updatedVariant = productVariantService.updateVariant(variantId, variantDTO, images);
            return ResponseEntity.ok(updatedVariant);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing variant JSON", e);
        }
    }
        

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{variantId}/variants")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long variantId) {
        productVariantService.deleteVariant(variantId);
        return ResponseEntity.noContent().build();
    }



    @PostMapping(value = "/{productId}/variants", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductVariantDTO> addVariant(
            @PathVariable Long productId,
            @RequestPart("variant") String variantJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ProductVariantDTO variantDTO = mapper.readValue(variantJson, ProductVariantDTO.class);
            
            // Log the number of received files
            System.out.println("Received " + (images != null ? images.length : 0) + " images");
            
            // Convert array to list for the service call
            List<MultipartFile> imageList = images != null ? java.util.Arrays.asList(images) : java.util.Collections.emptyList();
            ProductVariantDTO createdVariant = productVariantService.addVariant(productId, variantDTO, imageList);
            return ResponseEntity.ok(createdVariant);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing variant JSON", e);
        }
    }
    
    
    
}

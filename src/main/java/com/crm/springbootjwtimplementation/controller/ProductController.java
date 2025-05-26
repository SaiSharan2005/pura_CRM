package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.dto.ResponseMessageDTO;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductDTO;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductSummaryDTO;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductVariantDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.ProductService;
import com.crm.springbootjwtimplementation.service.ProductVariantService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductVariantService variantService;
    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProductController(
            ProductService productService,
            ProductVariantService variantService,
            AuthService authService,
            ObjectMapper objectMapper) {
        this.productService = productService;
        this.variantService = variantService;
        this.authService = authService;
        this.objectMapper = objectMapper;
    }

    /**
     * POST {{baseUrl}}/api/products
     * Create a new product for the authenticated user.
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> createProduct(
            @RequestPart("product") String productJson,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) throws IOException {
        ProductDTO dto = objectMapper.readValue(productJson, ProductDTO.class);

        TokenResponseDTO me = authService.getAuthenticatedUser();
        ProductDTO created = productService.createProduct(me.getId(), dto, thumbnail);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /**
     * GET {{baseUrl}}/api/products?page=0&size=20
     * List all products, paged.
     */
    @GetMapping
    public ResponseEntity<Page<ProductDTO>> listProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ProductDTO> results = productService.listProducts(pageable);
        return ResponseEntity.ok(results);
    }

    /**
     * GET {{baseUrl}}/api/products/{id}
     * Fetch one product by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO dto = productService.getProductById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * PUT {{baseUrl}}/api/products/{id}
     * Update a product.
     */
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestPart("product") String productJson,
            @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail) throws IOException {
        ProductDTO dto = objectMapper.readValue(productJson, ProductDTO.class);
        ProductDTO updated = productService.updateProduct(id, dto, thumbnail);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE {{baseUrl}}/api/products/{id}
     * Delete a product.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * GET {{baseUrl}}/api/products/search?q=foo&page=0&size=10
     * Search products by name (paged).
     */
    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @RequestParam("q") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ProductDTO> results = productService.searchProducts(q, pageable);
        return ResponseEntity.ok(results);
    }

    //
    // -- VARIANTS ENDPOINTS --
    //

    /**
     * POST {{baseUrl}}/api/products/{productId}/variants
     * Create a new variant (with optional images).
     */
    @PostMapping(value = "/{productId}/variants", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductVariantDTO> addVariant(
            @PathVariable Long productId,
            @RequestPart("variant") String variantJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {
        ProductVariantDTO dto = objectMapper.readValue(variantJson, ProductVariantDTO.class);
        List<MultipartFile> pics = (images != null ? List.of(images) : List.of());
        ProductVariantDTO created = variantService.addVariant(productId, dto, pics);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * PUT {{baseUrl}}/api/products/variants/{variantId}
     * Update a variant (with optional new images).
     */
    @PutMapping(value = "/variants/{variantId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductVariantDTO> updateVariant(
            @PathVariable Long variantId,
            @RequestPart("variant") String variantJson,
            @RequestPart(value = "images", required = false) MultipartFile[] images) throws IOException {
        ProductVariantDTO dto = objectMapper.readValue(variantJson, ProductVariantDTO.class);
        // List<MultipartFile> pics = (images != null ? List.of(images) : List.of());
        ProductVariantDTO updated = variantService.updateVariant(variantId, dto, images);
        return ResponseEntity.ok(updated);
    }

    /**
     * DELETE {{baseUrl}}/api/products/variants/{variantId}
     * Delete a variant by ID.
     */
    @DeleteMapping("/variants/{variantId}")
    public ResponseEntity<Void> deleteVariant(@PathVariable Long variantId) {
        variantService.deleteVariant(variantId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/active")
    public ResponseEntity<ResponseMessageDTO> activate(
            @PathVariable Long id,
            @RequestParam("flag") boolean flag) {
        productService.setActive(id, flag);

        ResponseMessageDTO response = new ResponseMessageDTO();
        if (flag) {
            response.setMessage("Succesfully Activated the product ");

        } else {
            response.setMessage("Succesfully Deactivated the product ");

        }
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/summaries")
    public ResponseEntity<Page<ProductSummaryDTO>> listSummaries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<ProductSummaryDTO> summaries = productService.listSummaries(pageable);

        return ResponseEntity.ok(summaries);
    }

      @PutMapping(value = "/{id}/thumbnail", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<ProductDTO> updateThumbnail(
      @PathVariable Long id,
      @RequestPart("thumbnail") MultipartFile thumbnail) {

    ProductDTO updated = productService.updateThumbnail(id, thumbnail);
    return ResponseEntity.ok(updated);
  }

}

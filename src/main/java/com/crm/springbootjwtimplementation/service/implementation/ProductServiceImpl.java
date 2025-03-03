package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Product;
import com.crm.springbootjwtimplementation.domain.ProductVariant;
import com.crm.springbootjwtimplementation.domain.ProductVariantImage;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.ProductDTO;
import com.crm.springbootjwtimplementation.domain.dto.ProductVariantDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.ProductRepository;
import com.crm.springbootjwtimplementation.repository.ProductVariantRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;    
    
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ProductDTO createProduct(Long userId, ProductDTO productDTO) {
        // Check each variant's SKU for uniqueness.
        if (productDTO.getVariants() != null) {
            for (ProductVariantDTO variantDTO : productDTO.getVariants()) {
                if (productVariantRepository.existsBySku(variantDTO.getSku())) {
                    throw new CustomSecurityException(
                            "Product variant with SKU " + variantDTO.getSku() + " already exists",
                            HttpStatus.CONFLICT);
                }
            }
        }
        // Retrieve user.
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomSecurityException("User not found with ID: " + userId, HttpStatus.NOT_FOUND));

        // Map DTO to entity.
        Product product = modelMapper.map(productDTO, Product.class);
        product.setCreatedDate(LocalDate.now());

        // Optionally set additional properties on product (e.g., user association) if
        // needed.
        // Ensure each variant references its parent product.
        if (product.getVariants() != null) {
            for (ProductVariant variant : product.getVariants()) {
                variant.setProduct(product);
            }
        }
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            if (product.getVariants() != null) {
                productDTO.setVariants(
                        product.getVariants().stream().map(variant -> {
                            ProductVariantDTO variantDTO = modelMapper.map(variant, ProductVariantDTO.class);
                            if (variant.getImages() != null) {
                                variantDTO.setImageUrls(
                                        variant.getImages().stream()
                                                .map(ProductVariantImage::getImageUrl)
                                                .collect(Collectors.toList()));
                            }
                            return variantDTO;
                        }).collect(Collectors.toList()));
            }
            return productDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(
                        () -> new CustomSecurityException("Product not found with ID: " + id, HttpStatus.NOT_FOUND));
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        if (product.getVariants() != null) {
            productDTO.setVariants(
                    product.getVariants().stream().map(variant -> {
                        ProductVariantDTO variantDTO = modelMapper.map(variant, ProductVariantDTO.class);
                        if (variant.getImages() != null) {
                            variantDTO.setImageUrls(
                                    variant.getImages().stream()
                                            .map(ProductVariantImage::getImageUrl)
                                            .collect(Collectors.toList()));
                        }
                        return variantDTO;
                    }).collect(Collectors.toList()));
        }
        return productDTO;
    }

    @Override
    public ProductDTO updateProductDetails(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Product not found with ID: " + id, HttpStatus.NOT_FOUND));
    
        BeanUtils.copyProperties(productDTO, existingProduct, getNullPropertyNames(productDTO));
    
        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
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

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new CustomSecurityException("Product not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
    }
}

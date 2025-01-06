
// Product Service Implementation
package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Product;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.ProductDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.ProductRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO createProduct(Long id , ProductDTO productDTO) {
        if (productRepository.existsBySku(productDTO.getSku())) {
            throw new CustomSecurityException("Product with this SKU already exists", HttpStatus.CONFLICT);
        }
        User user = userRepository.findById(id).get();
        productDTO.setUser(user);
        Product product = modelMapper.map(productDTO, Product.class);
        product = productRepository.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Product not found with ID: " + id, HttpStatus.NOT_FOUND));
        return modelMapper.map(product, ProductDTO.class);
    }

@Override
public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
    Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new CustomSecurityException("Product not found with ID: " + id, HttpStatus.NOT_FOUND));

    // Copy only non-null properties from DTO to the existing entity
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

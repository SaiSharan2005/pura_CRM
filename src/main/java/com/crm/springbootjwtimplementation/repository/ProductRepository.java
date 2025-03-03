package com.crm.springbootjwtimplementation.repository;

import com.crm.springbootjwtimplementation.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // No SKU methods here – SKU is now part of ProductVariant.
}

package com.crm.springbootjwtimplementation.domain;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "product_variant_image")
public class ProductVariantImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // For example, a URL or relative path to the stored image.
    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;
}

package com.crm.springbootjwtimplementation.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is mandatory")
    @Column(nullable = false)
    private String productName;

    @NotBlank(message = "Description is mandatory")
    @Column(nullable = false, length = 500)
    private String description;

    @NotBlank(message = "Product status is mandatory")
    @Column(nullable = false)
    private String productStatus;

    @NotNull
    @Column(nullable = false)
    private LocalDate createdDate;

        // Each product can have multiple variants.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductVariant> variants;
}

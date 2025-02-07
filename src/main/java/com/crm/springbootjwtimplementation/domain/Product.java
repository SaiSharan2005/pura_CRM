package com.crm.springbootjwtimplementation.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(nullable = false)
    private BigDecimal price;

    @NotBlank(message = "SKU is mandatory")
    @Column(nullable = false, unique = true)
    private String sku;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();

    @NotBlank(message = "Product status is mandatory")
    @Column(nullable = false)
    private String productStatus;

    @NotNull
    @Column(nullable = false)
    private Integer quantityAvailable;

    @NotBlank(message = "Dimensions are mandatory")
    @Column(nullable = false)
    private String dimensions;

    @Column(nullable = false)
    private Integer warrantyPeriod;

    @Column(nullable = false)
    private Double weight;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "product")
    @JsonIgnore // Ignore the cartItems field to prevent serialization of the reverse side
    private List<CartItem> cartItems;
}

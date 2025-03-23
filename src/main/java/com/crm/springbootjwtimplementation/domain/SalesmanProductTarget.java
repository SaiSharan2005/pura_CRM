package com.crm.springbootjwtimplementation.domain;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "salesman_product_targets")
public class SalesmanProductTarget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The salesman for whom the target is set.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salesman_id", nullable = false)
    private SalesmanDetails salesman;

    // The product variant for which the target is set.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    // The quantity target for the product.
    @Column(name = "product_quantity_target", nullable = false)
    private int productQuantityTarget;

    // Month-Year identifier, e.g. "2025-03"
    @Column(name = "month_year", nullable = false)
    private String monthYear;

    // Optional: The manager who approved/set the target (can be null)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id")
    private ManagerDetails manager;

    // Optional: If some quantity is carried over from a previous month.
    @Column(name = "carried_over_quantity")
    private Integer carriedOverQuantity;

    // Audit fields.
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }
    
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

package com.crm.springbootjwtimplementation.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Each inventory record links a product variant with a warehouse.
    @NotNull(message = "Product variant is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    @NotNull(message = "Warehouse is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    // Current stock level at the given warehouse.
    @NotNull(message = "Quantity is required")
    @Column(nullable = false)
    private Integer quantity;

    // Reorder threshold to trigger restocking.
    @NotNull(message = "Reorder level is required")
    @Column(nullable = false)
    private Integer reorderLevel;

    // Safety stock level to ensure availability.
    @NotNull(message = "Safety stock is required")
    @Column(nullable = false)
    private Integer safetyStock;

    // Automatically updated timestamp for tracking changes.
    @Column(nullable = false)
    private LocalDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        this.lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.lastUpdated = LocalDateTime.now();
    }
}

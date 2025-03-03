package com.crm.springbootjwtimplementation.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "inventory_movements")
public class InventoryMovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to the product variant associated with this movement.
    @NotNull(message = "Product variant is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_variant_id", nullable = false)
    private ProductVariant productVariant;

    // Link to the warehouse where the movement occurs.
    @NotNull(message = "Warehouse is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private Warehouse warehouse;

    // Indicates the type of movement (e.g., "IN", "OUT", "ADJUSTMENT").
    @NotBlank(message = "Movement type is mandatory")
    @Column(nullable = false)
    private String movementType;

    // The quantity being moved.
    @NotNull(message = "Quantity is required")
    @Column(nullable = false)
    private Integer quantity;

    // Timestamp for when the movement occurred.
    @Column(nullable = false)
    private LocalDateTime movementDate;

    // Optional reference (e.g., order number, adjustment document).
    private String reference;

    // Optional additional notes about the movement.
    private String note;

    @PrePersist
    public void prePersist() {
        this.movementDate = LocalDateTime.now();
    }
}

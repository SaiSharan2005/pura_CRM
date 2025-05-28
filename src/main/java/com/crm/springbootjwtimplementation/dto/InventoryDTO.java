package com.crm.springbootjwtimplementation.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InventoryDTO {
    private Long id;
    // Reference IDs for associations:
    private Long productVariantId;
    private Long warehouseId;
    
    private Integer quantity;
    private Integer reorderLevel;
    private Integer safetyStock;
    private LocalDateTime lastUpdated;
}

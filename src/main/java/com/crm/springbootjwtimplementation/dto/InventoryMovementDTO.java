package com.crm.springbootjwtimplementation.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InventoryMovementDTO {
    private Long id;
    private Long productVariantId;
    private Long warehouseId;
    private String movementType;
    private Integer quantity;
    private LocalDateTime movementDate;
    private String reference;
    private String note;
}

package com.crm.springbootjwtimplementation.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.math.BigDecimal;

@Data
public class SalesmanProductTargetResponseDTO {
    private Long id;
    private Long salesmanId;
    private Long productVariantId;
    private int productQuantityTarget;
    private String monthYear;
    private Long managerId; // Can be null
    private Integer carriedOverQuantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

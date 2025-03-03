package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;
    private String productName;
    private String description;
    private String productStatus;
    private LocalDate createdDate;  // Changed from LocalDateTime to LocalDate to match entity
    private List<ProductVariantDTO> variants;
}

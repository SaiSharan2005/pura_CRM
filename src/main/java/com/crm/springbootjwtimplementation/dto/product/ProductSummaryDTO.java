// src/main/java/com/crm/springbootjwtimplementation/domain/dto/product/ProductSummaryDTO.java
package com.crm.springbootjwtimplementation.dto.product;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ProductSummaryDTO {
    private Long id;
    private String productName;
    private String productStatus;
    private LocalDate createdDate;
    private String thumbnailUrl;
}

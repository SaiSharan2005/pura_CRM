package com.crm.springbootjwtimplementation.domain.dto;


import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data
public class ProductVariantDTO {
    private Long id;
    private String variantName;
    private BigDecimal price;
    private String sku;
    private String units;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedDate;
    private Long productId;
    private List<String> imageUrls; // or a list of DTOs for more details
}

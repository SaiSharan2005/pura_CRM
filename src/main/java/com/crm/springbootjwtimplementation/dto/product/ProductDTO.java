// src/main/java/com/crm/springbootjwtimplementation/domain/dto/product/ProductDTO.java
package com.crm.springbootjwtimplementation.dto.product;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProductDTO {
    private Long id;

    @NotBlank(message = "Product name is mandatory")
    private String productName;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Status is mandatory")
    private String productStatus;

    // server‐set
    private LocalDate createdDate;

    // new:
    private String thumbnailUrl;

    private List<ProductVariantDTO> variants;
}

// src/main/java/com/crm/springbootjwtimplementation/domain/dto/ProductVariantDTO.java
package com.crm.springbootjwtimplementation.domain.dto.product;

import lombok.Data;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductVariantDTO {

    private Long   id;

    @NotBlank(message = "Variant name is mandatory")
    private String variantName;

    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @NotBlank(message = "SKU is mandatory")
    private String sku;

    @NotBlank(message = "Units is mandatory")
    private String units;

    // audit fields
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    // back-reference for client to know parent product
    private Long productId;

    // flattened image URLs
    private List<ProductVariantImageDTO> imageUrls;
}

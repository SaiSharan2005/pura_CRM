package com.crm.springbootjwtimplementation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import com.crm.springbootjwtimplementation.domain.ProductVariant;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductVariantDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private ProductVariantDTO productVariant;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}

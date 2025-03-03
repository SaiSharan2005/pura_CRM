package com.crm.springbootjwtimplementation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import com.crm.springbootjwtimplementation.domain.ProductVariant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    private Long id;
    private ProductVariant productVariant;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}

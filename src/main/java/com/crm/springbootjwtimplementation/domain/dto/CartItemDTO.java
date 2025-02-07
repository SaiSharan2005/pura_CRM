package com.crm.springbootjwtimplementation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import com.crm.springbootjwtimplementation.domain.Product;

@Getter
@Setter
@AllArgsConstructor
public class CartItemDTO {
    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;
}

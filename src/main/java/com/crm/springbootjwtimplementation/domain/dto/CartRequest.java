package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Long cartId;
    private Long variantId;
    private int quantity;

}

package com.crm.springbootjwtimplementation.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Long cartId;
    private Long variantId;
    private int quantity;

}

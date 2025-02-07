package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;

@Data
public class CartRequest {
    private Long cartId;
    private Long productId;
    private int quantity;

}

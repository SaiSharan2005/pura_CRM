package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartDTO {
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private String status;
    private LocalDateTime updatedAt;
    private List<CartItemDTO> cartItems;
}

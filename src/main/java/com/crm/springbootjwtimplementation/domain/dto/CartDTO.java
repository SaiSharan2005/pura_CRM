package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;  // ✅ Add this for a no-args constructor

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  // ✅ Fix: Add a no-arg constructor
public class CartDTO {
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private String status;
    private LocalDateTime updatedAt;
    private List<CartItemDTO> cartItems;
}

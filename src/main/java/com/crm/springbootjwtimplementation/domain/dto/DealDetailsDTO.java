package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;

import javax.validation.constraints.*;

import com.crm.springbootjwtimplementation.domain.Cart;
import com.crm.springbootjwtimplementation.domain.Customer;
import com.crm.springbootjwtimplementation.domain.SalesmanDetails;
import com.crm.springbootjwtimplementation.domain.User;

import java.time.LocalDate;

@Data
public class DealDetailsDTO {

    @NotNull(message = "Deal ID cannot be null")
    private Long id;

    @NotNull(message = "Customer ID cannot be null")
    private Customer customerId;

    @NotNull(message = "Cart ID cannot be null")
    private CartDTO cartId;

    @NotNull(message = "User ID cannot be null")
    private User userId;

    @NotBlank(message = "Deal name cannot be blank")
    private String dealName;

    @NotBlank(message = "Deal stage cannot be blank")
    private String dealStage;

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotBlank(message = "Delivery addraess cannot be blank")
    private String deliveryAddress;

    @NotNull(message = "Expected close date cannot be null")
    @Future(message = "Expected close date must be in the future")
    private LocalDate expectedCloseDate;

    private LocalDate actualClosedDate;

    private String note;
}

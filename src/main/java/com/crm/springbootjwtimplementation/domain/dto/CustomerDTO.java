
// Customer DTO
package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CustomerDTO {
    private Long id;
    @NotBlank(message = "Customer name is mandatory")
    private String customerName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "Number of orders cannot be null")
    private Integer noOfOrders;

    @NotBlank(message = "Buyer company name is mandatory")
    private String buyerCompanyName;
}

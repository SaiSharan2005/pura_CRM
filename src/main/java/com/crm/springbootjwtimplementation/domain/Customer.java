// Customer Entity
package com.crm.springbootjwtimplementation.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Customer name is mandatory")
    @Column(nullable = false)
    private String customerName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Phone number is mandatory")
    @Column(nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "Number of orders cannot be null")
    @Column(nullable = false)
    private Integer noOfOrders;

    @NotBlank(message = "Buyer company name is mandatory")
    @Column(nullable = false)
    private String buyerCompanyName;


}

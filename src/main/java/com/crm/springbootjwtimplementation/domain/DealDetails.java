// Deal Entity
package com.crm.springbootjwtimplementation.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "dealDetails")
public class DealDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customerId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cartId;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // This column should exist in the table
    private User userId;

    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "cart_id", nullable = false)
    // @JsonIgnore
    // private Cart cartId;

    // @ManyToOne(fetch = FetchType.LAZY, optional = false)
    // @JoinColumn(name = "salesman_id", nullable = false)
    // @JsonIgnore
    // private SalesmanDetails salesmanId;

    @NotBlank(message = "Deal name is mandatory")
    @Column(nullable = false)
    private String dealName;

    @NotBlank(message = "Deal stage is mandatory")
    @Column(nullable = false)
    private String dealStage;

    @NotNull(message = "Amount is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotBlank(message = "Delivery address is mandatory")
    @Column(nullable = false)
    private String deliveryAddress;

    @NotNull(message = "Expected close date is mandatory")
    @Future(message = "Expected close date must be in the future")
    @Column(nullable = false)
    private LocalDate expectedCloseDate;

    private LocalDate actualClosedDate;

    private String note;
}
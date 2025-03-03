package com.crm.springbootjwtimplementation.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "deal_details")
public class DealDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Use a more descriptive field name for the associated customer.
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Use "cart" instead of "cartId" since it represents an entity.
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    // Renamed for clarity.
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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

    // Optional: If you need to view all deliveries related to this deal, add a bidirectional mapping.
    @OneToMany(mappedBy = "deal", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Delivery> deliveries;
}

package com.crm.springbootjwtimplementation.domain;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one association with DealDetails.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deal_id", nullable = false)
    private DealDetails deal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logistic_person_id", nullable = false)
    private LogisticPersonDetails logisticPerson;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    @NotBlank(message = "Status is mandatory")
    @Column(nullable = false)
    private String status;

    @NotBlank(message = "Delivery address is mandatory")
    @Column(nullable = false)
    private String deliveryAddress;

    @NotBlank(message = "Vehicle number is mandatory")
    @Column(nullable = false)
    private String vehicleNumber;

    @NotBlank(message = "License number is mandatory")
    @Column(nullable = false)
    private String licenseNumber;
}

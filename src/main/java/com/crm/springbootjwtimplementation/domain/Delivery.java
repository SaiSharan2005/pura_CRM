package com.crm.springbootjwtimplementation.domain;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.Data;

@Data
@Entity
@Table(name = "delivery")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "deal_id", nullable = false)
    private DealDetails deal;

    @ManyToOne
    @JoinColumn(name = "logistic_person_id", nullable = false)
    private LogisticPersonDetails logisticPerson;

    @Column(nullable = false)
    private LocalDate deliveryDate;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String deliveryAddress;

    @Column(nullable = false)
    private String vehicleNumber;

    @Column(nullable = false)
    private String licenseNumber;
}

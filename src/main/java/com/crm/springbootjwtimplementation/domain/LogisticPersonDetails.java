package com.crm.springbootjwtimplementation.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "logisticPersonDetails")
public class LogisticPersonDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Phone number is mandatory")
    @Column(nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be a past date")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Delivery areas cannot be blank")
    @Column(nullable = false)
    private String deliveryAreas;

    @NotBlank(message = "Vehicle information is mandatory")
    @Column(nullable = false)
    private String vehicleInfo;

    @NotBlank(message = "License number is mandatory")
    @Column(nullable = false)
    private String licenseNumber;

    @NotBlank(message = "Status is mandatory")
    @Column(nullable = false)
    private String status = "ACTIVE";

    private String notes;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
    private User user;
}

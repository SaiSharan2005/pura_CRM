package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;

import javax.validation.constraints.*;

import com.crm.springbootjwtimplementation.domain.User;

import java.time.LocalDate;

@Data
public class LogisticPersonDetailsDTO {

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Delivery areas cannot be blank")
    private String deliveryAreas;

    @NotBlank(message = "Vehicle information is mandatory")
    private String vehicleInfo;

    @NotBlank(message = "License number is mandatory")
    private String licenseNumber;

    @NotBlank(message = "Status is mandatory")
    private String status = "ACTIVE";

    private String notes;

    private User user;
}

package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DeliveryRequestDTO {

    private Long dealId; // Pass only the ID
    private Long logisticPersonId; // Pass only the ID
    private LocalDate deliveryDate;
    private String status;
    private String deliveryAddress;
    private String vehicleNumber;
    private String licenseNumber;
}

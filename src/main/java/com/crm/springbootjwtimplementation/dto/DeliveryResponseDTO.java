package com.crm.springbootjwtimplementation.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DeliveryResponseDTO {

    private Long id;
    private Long dealId; // Only include the ID
    private Long logisticPersonId; // Only include the ID
    private LocalDate deliveryDate;
    private String status;
    private String deliveryAddress;
    private String vehicleNumber;
    private String licenseNumber;
}

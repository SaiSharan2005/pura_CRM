package com.crm.springbootjwtimplementation.domain.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TargetResponseDTO {
    private Long id;
    private String description;
    private LocalDate deadline;
    private boolean isAchieved;
    private int monthlyOutletCount;
    private BigDecimal monthlyTargetAmount;
    // For simplicity, only include the IDs of manager and salesman here.
    private Long managerId;
    private Long salesmanId;
    private String monthYear;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

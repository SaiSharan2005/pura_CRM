package com.crm.springbootjwtimplementation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class TargetRequestDTO {
    private String description;
    private LocalDate deadline;
    private int monthlyOutletCount;
    private BigDecimal monthlyTargetAmount;
    // For creation the manager id is taken from the authenticated user.
    // The salesman id is provided in the request.
    private Long salesmanId;
    // The month-year string in the format "YYYY-MM"
    private String monthYear;
}

package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TargetRequestDTO {
    private String description;
    private LocalDate deadline;
    private Boolean isAchieved;
    private Long assignedToIds;
    private Long assignedByIds;
    private String assigneeRole;
}

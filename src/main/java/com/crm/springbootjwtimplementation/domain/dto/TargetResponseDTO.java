package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.crm.springbootjwtimplementation.domain.User;

@Data
public class TargetResponseDTO {
    private Long id;
    private String description;
    private LocalDate deadline;
    private Boolean isAchieved;
    private User assignedToId;
    private User assignedById;
    private String assigneeRole;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

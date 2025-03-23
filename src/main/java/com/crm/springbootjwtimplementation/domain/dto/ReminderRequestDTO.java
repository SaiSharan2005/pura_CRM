package com.crm.springbootjwtimplementation.domain.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReminderRequestDTO {
    private String title;
    private String note;
    private String location;
    private Long dealId; // optional
    private LocalDateTime reminderDate;
}

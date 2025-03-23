package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReminderResponseDTO {
    private Long id;
    private String title;
    private String note;
    private String location;
    /**
     * For some endpoints, only the deal ID is returned.
     * In the GET /{id} endpoint, you could expand this field
     * (for example, by including deal details in another DTO).
     */
    private Long dealId;
    private Long userId;
    private boolean active;
    private LocalDateTime reminderDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

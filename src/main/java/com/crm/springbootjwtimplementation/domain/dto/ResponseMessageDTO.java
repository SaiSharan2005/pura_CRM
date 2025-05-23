package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;

@Data
public class ResponseMessageDTO {
    private String message;
    private boolean success;
}

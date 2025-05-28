package com.crm.springbootjwtimplementation.dto;

import lombok.Data;

@Data
public class SalesmanProductTargetRequestDTO {
    private Long productVariantId;
    private int productQuantityTarget;
    private String monthYear;
    // Optional manager id – may be null.
    private Long managerId;
    // Optional carried-over quantity.
    private Integer carriedOverQuantity;
}

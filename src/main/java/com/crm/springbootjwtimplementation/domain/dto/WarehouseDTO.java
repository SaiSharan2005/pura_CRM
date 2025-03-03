package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class WarehouseDTO {
    private Long id;
    private String warehouseName;
    private String location;
    private LocalDate createdDate;
}

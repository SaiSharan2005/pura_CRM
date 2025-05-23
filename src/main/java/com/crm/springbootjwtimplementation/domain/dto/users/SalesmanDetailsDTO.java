// SalesmanDetailsDTO.java (for create/update)
package com.crm.springbootjwtimplementation.domain.dto.users;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Getter
@Setter
public class SalesmanDetailsDTO {
    @NotNull(message = "User ID is mandatory")
    private Long userId;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Region assigned is mandatory")
    private String regionAssigned;

    @NotNull(message = "Total sales is mandatory")
    private Integer totalSales;

    @NotNull(message = "Hire date is mandatory")
    @Past(message = "Hire date must be in the past")
    private LocalDate hireDate;

    @NotBlank(message = "Status is mandatory")
    private String status;

    private String notes;
}

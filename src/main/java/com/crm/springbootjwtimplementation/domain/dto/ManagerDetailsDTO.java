package com.crm.springbootjwtimplementation.domain.dto;

import javax.validation.constraints.*;
import com.crm.springbootjwtimplementation.domain.User;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ManagerDetailsDTO {

    private User user;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be a past date")
    private LocalDate dateOfBirth;

    private String status = "ACTIVE"; // Default value for status

    // @NotBlank(message = "Notes cannot be blank")
    // private String notes;

    @NotNull(message = "Hire date cannot be null")
    @Past(message = "Hire date must be a past date")
    private LocalDate hireDate;
}

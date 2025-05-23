// ManagerDetailsDTO.java  (input for create/update)
package com.crm.springbootjwtimplementation.domain.dto.users;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class ManagerDetailsDTO {
    @NotNull(message = "User ID is mandatory")
    private Long userId;

    @NotBlank(message = "Phone number cannot be blank")
    private String phoneNumber;

    @NotBlank(message = "Address cannot be blank")
    private String address;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotNull(message = "Hire date cannot be null")
    @Past(message = "Hire date must be in the past")
    private LocalDate hireDate;

    @NotBlank(message = "Status is mandatory")
    private String status;

}

// ManagerDetailsResponseDTO.java  (output)
package com.crm.springbootjwtimplementation.dto.users;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ManagerDetailsResponseDTO {
    private Long id;
    private UserDTO user;               // Nested user data
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private LocalDate hireDate;
    private String status;
    private String notes;
}

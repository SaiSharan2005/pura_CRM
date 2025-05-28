// SalesmanDetailsResponseDTO.java (for reads, includes nested user)
package com.crm.springbootjwtimplementation.dto.users;

import lombok.Data;
import java.time.LocalDate;

@Data
public class SalesmanDetailsResponseDTO {
    private Long id;
    private UserDTO user;               // Nested user data
    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private String regionAssigned;
    private Integer totalSales;
    private LocalDate hireDate;
    private String status;
    private String notes;
}

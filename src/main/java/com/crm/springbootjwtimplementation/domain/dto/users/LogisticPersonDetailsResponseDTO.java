// src/main/java/com/crm/springbootjwtimplementation/domain/dto/logistic/LogisticPersonDetailsResponseDTO.java
package com.crm.springbootjwtimplementation.domain.dto.users;

import lombok.Data;
import java.time.LocalDate;
// import com.crm.springbootjwtimplementation.domain.dto.users.UserDTO;

/**
 * Outgoing payload for all reads.
 */
@Data
public class LogisticPersonDetailsResponseDTO {

    /** Auto-generated PK */
    private Long id;

    /** Nested user info (mapped via UserMapper) */
    private UserDTO user;

    private String phoneNumber;
    private String address;
    private LocalDate dateOfBirth;
    private String deliveryAreas;
    private String vehicleInfo;
    private String licenseNumber;
    private String status;
    private String notes;
}

// src/main/java/com/crm/springbootjwtimplementation/domain/dto/logistic/LogisticPersonDetailsDTO.java
package com.crm.springbootjwtimplementation.dto.users;

import lombok.Data;
import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Incoming payload for create & update.
 */
@Data
public class LogisticPersonDetailsDTO {

    /** The FK to your users table; controller/service populates this from the auth token. */
    @NotNull(message = "User ID is mandatory")
    private Long userId;

    @NotBlank(message = "Phone number is mandatory")
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "Date of birth cannot be null")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Delivery areas cannot be blank")
    private String deliveryAreas;

    @NotBlank(message = "Vehicle information is mandatory")
    private String vehicleInfo;

    @NotBlank(message = "License number is mandatory")
    private String licenseNumber;

    /**  Defaults to ACTIVE if not provided */
    private String status = "ACTIVE";

    /** Optional notes */
    private String notes;
}

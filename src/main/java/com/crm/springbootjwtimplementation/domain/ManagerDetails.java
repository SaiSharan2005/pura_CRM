// Manager Entity
package com.crm.springbootjwtimplementation.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "managerDetails")
public class ManagerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Phone number is mandatory")
    @Column(nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "Phone number is mandatory")
    @Column(nullable = false)
    private LocalDate dateOfBirth;


    @NotBlank(message = "Manager status is mandatory")
    @Column(nullable = false)
    private String status = "ACTIVE";



    @NotNull(message = "Hire date is mandatory")
    @Past(message = "Hire date must be in the past")
    @Column(nullable = false)
    private LocalDate hireDate;



    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true, nullable = false)
    private User user;
}

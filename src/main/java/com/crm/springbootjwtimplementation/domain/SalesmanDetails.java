package com.crm.springbootjwtimplementation.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import lombok.Data;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "salesmanDetails")
public class SalesmanDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Phone number is mandatory")
    @Column(nullable = false)
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    @Column(nullable = false)
    private String address;

    @NotNull(message = "Date of birth is mandatory")
    @Past(message = "Date of birth must be in the past")
    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @NotBlank(message = "Region assigned is mandatory")
    @Column(nullable = false)
    private String regionAssigned;

    @NotNull(message = "Total sales is mandatory")
    @Column(nullable = false)
    private Integer totalSales;

    @NotNull(message = "Hire date is mandatory")
    @Column(nullable = false)
    private LocalDate hireDate;

    @NotBlank(message = "Status is mandatory")
    @Column(nullable = false)
    private String status;

    private String notes;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true , nullable = false)
    private User user;


//     @OneToOne
// @JoinColumn(name = "user_id", referencedColumnName = "id")
// private User user;

}

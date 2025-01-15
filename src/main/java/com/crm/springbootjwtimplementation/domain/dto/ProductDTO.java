// Product DTO
package com.crm.springbootjwtimplementation.domain.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.*;

import com.crm.springbootjwtimplementation.domain.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductDTO {
    // @NotBlank(message = "Product name is mandatory")
    private Long id;

    @NotBlank(message = "Product name is mandatory")
    private String productName;

    @NotBlank(message = "Description is mandatory")
    private String description;
 
    @NotNull(message = "Price is mandatory")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "SKU is mandatory")
    private String sku;

    // @NotNull(message = "Created date is mandatory")
    // private LocalDateTime createdDate;

    @NotBlank(message = "Product status is mandatory")
    private String productStatus;

    // @NotNull(message = "Duration is mandatory")
    // private Integer duration;

    @NotNull(message = "Weight is mandatory")
    private Double weight;

    @NotNull(message = "Warranty period is mandatory")
    private Integer warrantyPeriod;


    @NotBlank(message = "Dimensions are mandatory")
    private String dimensions;

    @NotBlank(message = "Quantity available is mandatory")
    private Integer quantityAvailable;

    private User user;


}  

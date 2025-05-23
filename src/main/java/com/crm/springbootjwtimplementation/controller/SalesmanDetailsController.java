package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.dto.ResponseMessageDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.SalesmanDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.SalesmanDetailsResponseDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.SalesmanDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/salesman-details")
@Validated
public class SalesmanDetailsController {

    private final SalesmanDetailsService salesmanService;
    private final AuthService authService;

    @Autowired
    public SalesmanDetailsController(
            SalesmanDetailsService salesmanService,
            AuthService authService) {
        this.salesmanService = salesmanService;
        this.authService      = authService;
    }

    /** Create a new SalesmanDetails for the authenticated user */
    @PostMapping
    public ResponseEntity<SalesmanDetailsResponseDTO> createSalesman(
            @Valid @RequestBody SalesmanDetailsDTO dto) {

        TokenResponseDTO currentUser = authService.getAuthenticatedUser();
        SalesmanDetailsResponseDTO created =
            salesmanService.createSalesmanDetails(currentUser.getId(), dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(created);
    }    
    @PostMapping("/{userId}")
    public ResponseEntity<SalesmanDetailsResponseDTO> createSalesmanByAdmin(
            @Valid @RequestBody SalesmanDetailsDTO dto,
            @PathVariable Long userId) {

        SalesmanDetailsResponseDTO created =
            salesmanService.createSalesmanDetails(userId, dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(created);
    }

    /** Paginated list of all SalesmanDetails */
    @GetMapping
    public ResponseEntity<Page<SalesmanDetailsResponseDTO>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<SalesmanDetailsResponseDTO> dtos =
            salesmanService.getAllSalesmanDetails(page, size);

        return ResponseEntity.ok(dtos);
    }

    /** Get a SalesmanDetails by its User ID */
    @GetMapping("/user/{userId}")
    public ResponseEntity<SalesmanDetailsResponseDTO> getByUserId(
            @PathVariable Long userId) {

        SalesmanDetailsResponseDTO dto =
            salesmanService.getSalesmanDetailsByUserId(userId);

        return ResponseEntity.ok(dto);
    }

    /** Update SalesmanDetails by its User ID */
    @PutMapping("/user/{userId}")
    public ResponseEntity<SalesmanDetailsResponseDTO> updateByUserId(
            @PathVariable Long userId,
            @Valid @RequestBody SalesmanDetailsDTO dto) {

        SalesmanDetailsResponseDTO updated =
            salesmanService.updateSalesmanDetailsByUserId(userId, dto);

        return ResponseEntity.ok(updated);
    }

    /** Delete SalesmanDetails by its User ID */
@DeleteMapping("/user/{userId}")
public ResponseEntity<ResponseMessageDTO> deleteByUserId(@PathVariable Long userId) {
    salesmanService.deleteSalesmanDetailsByUserId(userId);

    ResponseMessageDTO response = new ResponseMessageDTO();
    response.setMessage("Salesman details for user " + userId + " deleted successfully");
    response.setSuccess(true);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
}

    /** Get the authenticated user’s own SalesmanDetails (by username) */
    @GetMapping("/me")
    public ResponseEntity<SalesmanDetailsResponseDTO> getOwnDetails(Principal principal) {
        SalesmanDetailsResponseDTO dto =
            salesmanService.getSalesmanDetailsByUsername(principal.getName());
        return ResponseEntity.ok(dto);
    }

    /** Update the authenticated user’s own SalesmanDetails */
    @PutMapping("/me")
    public ResponseEntity<SalesmanDetailsResponseDTO> updateOwnDetails(
            Principal principal,
            @Valid @RequestBody SalesmanDetailsDTO dto) {

        SalesmanDetailsResponseDTO updated =
            salesmanService.updateSalesmanDetailsByUsername(principal.getName(), dto);

        return ResponseEntity.ok(updated);
    }
}

package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.dto.SalesmanDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.SalesmanDetailsService;
import com.crm.springbootjwtimplementation.util.Security.AccessToken;
import com.crm.springbootjwtimplementation.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/salesman-details")
public class SalesmanDetailsController {

    @Autowired
    private SalesmanDetailsService salesmanDetailsService;

    @Autowired
    private AuthService AuthService;

    // POST /createSalesman
    @PostMapping("/createSalesman")
    public ResponseEntity<?> createSalesman(@Valid @RequestBody SalesmanDetailsDTO salesmanDetailsDTO) {
        // try {

        TokenResponseDTO user = AuthService.getAuthenticatedUser();
            return ResponseEntity.ok(salesmanDetailsService.createSalesmanDetails(user.getId(),salesmanDetailsDTO));
        // } catch (ValidationException e) {
        //     return ResponseEntity.badRequest().body(e.getMessage());
        // }
    }

    // GET / (get about themselves using Principal)
    @GetMapping("/")
    public ResponseEntity<?> getSalesmanDetailsAboutSelf(Principal principal) {
        // try {
            System.out.println(principal.getName());
            return ResponseEntity.ok(salesmanDetailsService.getSalesmanDetailsByUsername(principal.getName()));
        // } catch (Exception e) {
        //     return ResponseEntity.badRequest().body(e.getMessage());
        // }
    }

    // GET /id (get salesman detpails by ID)
    @GetMapping("/{id}")
    public ResponseEntity<?> getSalesmanDetailsById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(salesmanDetailsService.getSalesmanDetailsById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSalesmanById(@PathVariable Long id, @Valid @RequestBody SalesmanDetailsDTO salesmanDetailsDTO) {
        try {
            return ResponseEntity.ok(salesmanDetailsService.updateSalesmanDetailsById(id, salesmanDetailsDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // DELETE /id (delete salesman by ID)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSalesmanById(@PathVariable Long id) {
        try {
            salesmanDetailsService.deleteSalesmanDetailsById(id);
            return ResponseEntity.ok("Salesman deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT / (update themselves)
    @PutMapping("/")
    public ResponseEntity<?> updateSalesmanAboutSelf(Principal principal, @Valid @RequestBody SalesmanDetailsDTO salesmanDetailsDTO) {
        try {
            TokenResponseDTO userToken = AuthService.getAuthenticatedUser();
            return ResponseEntity.ok(salesmanDetailsService.updateSalesmanDetailsById(userToken.getId(), salesmanDetailsDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /all (get all salesman details)
    @GetMapping("/all")
    public ResponseEntity<List<SalesmanDetailsDTO>> getAllSalesmanDetails() {
        return ResponseEntity.ok(salesmanDetailsService.getAllSalesmanDetails());
    }
}

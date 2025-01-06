package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.dto.ManagerDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.ManagerDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/managers")
public class ManagerDetailsController {

    @Autowired
    private ManagerDetailsService managerDetailsService;

    @Autowired
    private AuthService AuthService;


    @PostMapping("/create")
    public ResponseEntity<ManagerDetailsDTO> createManager(
            @Validated @RequestBody ManagerDetailsDTO managerDetailsDTO) {

        TokenResponseDTO userToken = AuthService.getAuthenticatedUser();

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(managerDetailsService.createManagerDetails(userToken.getId(),managerDetailsDTO));
    }

        // GET / (get about themselves using Principal)
    @GetMapping("/")
    public ResponseEntity<?> getManagerDetailsAboutSelf(Principal principal) {
        // try {
            TokenResponseDTO userToken = AuthService.getAuthenticatedUser();
            return ResponseEntity.ok(managerDetailsService.getManagerDetailsById(userToken.getId()));
        // } catch (Exception e) {
        //     return ResponseEntity.badRequest().body(e.getMessage());
        // }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ManagerDetailsDTO>> getAllManagers() {
        return ResponseEntity.ok(managerDetailsService.getAllManagerDetails());
    }


    @GetMapping("/{id}")
    public ResponseEntity<ManagerDetailsDTO> getManagerById(@PathVariable Long id) {
        return ResponseEntity.ok(managerDetailsService.getManagerDetailsById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManagerDetailsDTO> updateManagerById(@PathVariable Long id,
            @Validated @RequestBody ManagerDetailsDTO managerDetailsDTO) {
        return ResponseEntity.ok(managerDetailsService.updateManagerDetailsById(id, managerDetailsDTO));
    }
    @PutMapping("/")
    public ResponseEntity<ManagerDetailsDTO> updateManagerDetailsAboutSelf(@Validated @RequestBody ManagerDetailsDTO managerDetailsDTO) {
        TokenResponseDTO userToken = AuthService.getAuthenticatedUser();

        return ResponseEntity.ok(managerDetailsService.updateManagerDetailsById(userToken.getId(), managerDetailsDTO));
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteManagerBySelf() {
        TokenResponseDTO userToken = AuthService.getAuthenticatedUser();
        
        managerDetailsService.deleteManagerDetailsById(userToken.getId());
        return ResponseEntity.noContent().build();
    } 
       
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManagerById(@PathVariable Long id) {
        managerDetailsService.deleteManagerDetailsById(id);
        return ResponseEntity.noContent().build();
    }
}

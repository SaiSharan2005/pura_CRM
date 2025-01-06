package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.dto.LogisticPersonDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.LogisticPersonDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logistics")
public class LogisticPersonDetailsController {

    @Autowired
    private LogisticPersonDetailsService service;

    @Autowired
    private AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<LogisticPersonDetailsDTO> createLogisticPerson(@RequestBody LogisticPersonDetailsDTO dto) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        return ResponseEntity.ok(service.createLogisticPersonDetails(userToken.getId(), dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LogisticPersonDetailsDTO>> getAllLogisticPersons() {
        return ResponseEntity.ok(service.getAllLogisticPersons());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LogisticPersonDetailsDTO> getLogisticPersonById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getLogisticPersonById(id));
    }   
     @GetMapping("/")
    public ResponseEntity<LogisticPersonDetailsDTO> getLogisticPersonBySelf() {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        return ResponseEntity.ok(service.getLogisticPersonById(userToken.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LogisticPersonDetailsDTO> updateLogisticPersonById(
            @PathVariable Long id, @RequestBody LogisticPersonDetailsDTO dto) {
        return ResponseEntity.ok(service.updateLogisticPersonById(id, dto));
    }   
     @PutMapping("/")
    public ResponseEntity<LogisticPersonDetailsDTO> updateLogisticPersonByThemSelf(
        @RequestBody LogisticPersonDetailsDTO dto) {
                TokenResponseDTO userToken = authService.getAuthenticatedUser();

        return ResponseEntity.ok(service.updateLogisticPersonById(userToken.getId(), dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLogisticPersonById(@PathVariable Long id) {
        service.deleteLogisticPersonById(id);
        return ResponseEntity.noContent().build();
    }
}

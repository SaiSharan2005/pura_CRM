package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.dto.DeliveryRequestDTO;
import com.crm.springbootjwtimplementation.dto.DeliveryResponseDTO;
import com.crm.springbootjwtimplementation.dto.users.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<DeliveryResponseDTO> createDelivery(@RequestBody DeliveryRequestDTO requestDTO) {

        return ResponseEntity.ok(deliveryService.createDelivery(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> getDeliveryById(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.getDeliveryById(id));
    }    
    @GetMapping
    public ResponseEntity<List<DeliveryResponseDTO>> getDeliveryOfUser() {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        return ResponseEntity.ok(deliveryService.getAllDeliveriesOfUser(userToken.getId()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<DeliveryResponseDTO>> getAllDeliveries() {
        return ResponseEntity.ok(deliveryService.getAllDeliveries());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponseDTO> updateDelivery(@PathVariable Long id,
            @RequestBody DeliveryRequestDTO requestDTO) {
        return ResponseEntity.ok(deliveryService.updateDelivery(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDelivery(@PathVariable Long id) {
        return ResponseEntity.ok(deliveryService.deleteDelivery(id));
    }


}

package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.dto.InventoryMovementDTO;
import com.crm.springbootjwtimplementation.service.InventoryMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventory-movements")
public class InventoryMovementController {

    @Autowired
    private InventoryMovementService inventoryMovementService;

    @PostMapping("/create")
    public ResponseEntity<InventoryMovementDTO> createInventoryMovement(@RequestBody InventoryMovementDTO inventoryMovementDTO) {
        InventoryMovementDTO createdMovement = inventoryMovementService.createInventoryMovement(inventoryMovementDTO);
        return ResponseEntity.ok(createdMovement);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InventoryMovementDTO> updateInventoryMovement(@PathVariable Long id,
                                                                        @RequestBody InventoryMovementDTO inventoryMovementDTO) {
        InventoryMovementDTO updatedMovement = inventoryMovementService.updateInventoryMovement(id, inventoryMovementDTO);
        return ResponseEntity.ok(updatedMovement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventoryMovement(@PathVariable Long id) {
        inventoryMovementService.deleteInventoryMovement(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryMovementDTO>> getAllInventoryMovements() {
        List<InventoryMovementDTO> movements = inventoryMovementService.getAllInventoryMovements();
        return ResponseEntity.ok(movements);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InventoryMovementDTO> getInventoryMovementById(@PathVariable Long id) {
        InventoryMovementDTO movementDTO = inventoryMovementService.getInventoryMovementById(id);
        return ResponseEntity.ok(movementDTO);
    }
}

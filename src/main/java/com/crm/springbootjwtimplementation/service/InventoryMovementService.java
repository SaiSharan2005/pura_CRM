package com.crm.springbootjwtimplementation.service;

import java.util.List;

import com.crm.springbootjwtimplementation.dto.InventoryMovementDTO;

public interface InventoryMovementService {
    InventoryMovementDTO createInventoryMovement(InventoryMovementDTO inventoryMovementDTO);
    InventoryMovementDTO updateInventoryMovement(Long id, InventoryMovementDTO inventoryMovementDTO);
    void deleteInventoryMovement(Long id);
    List<InventoryMovementDTO> getAllInventoryMovements();
    InventoryMovementDTO getInventoryMovementById(Long id);
}

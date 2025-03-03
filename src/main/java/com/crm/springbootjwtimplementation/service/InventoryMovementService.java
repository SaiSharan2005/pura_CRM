package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.InventoryMovementDTO;
import java.util.List;

public interface InventoryMovementService {
    InventoryMovementDTO createInventoryMovement(InventoryMovementDTO inventoryMovementDTO);
    InventoryMovementDTO updateInventoryMovement(Long id, InventoryMovementDTO inventoryMovementDTO);
    void deleteInventoryMovement(Long id);
    List<InventoryMovementDTO> getAllInventoryMovements();
    InventoryMovementDTO getInventoryMovementById(Long id);
}

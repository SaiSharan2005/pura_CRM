package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.InventoryDTO;
import java.util.List;

public interface InventoryService {
    InventoryDTO createInventory(InventoryDTO inventoryDTO);
    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO);
    void deleteInventory(Long id);
    List<InventoryDTO> getAllInventories();
    InventoryDTO getInventoryById(Long id);
}

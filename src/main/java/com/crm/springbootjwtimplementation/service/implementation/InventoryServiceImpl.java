package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Inventory;
import com.crm.springbootjwtimplementation.domain.ProductVariant;
import com.crm.springbootjwtimplementation.domain.Warehouse;
import com.crm.springbootjwtimplementation.domain.dto.InventoryDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.InventoryRepository;
import com.crm.springbootjwtimplementation.repository.ProductVariantRepository;
import com.crm.springbootjwtimplementation.repository.WarehouseRepository;
import com.crm.springbootjwtimplementation.service.InventoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InventoryDTO createInventory(InventoryDTO inventoryDTO) {
        Inventory inventory = new Inventory();
        // Set numeric fields:
        inventory.setQuantity(inventoryDTO.getQuantity());
        inventory.setReorderLevel(inventoryDTO.getReorderLevel());
        inventory.setSafetyStock(inventoryDTO.getSafetyStock());

        // Set associations using provided IDs:
        ProductVariant productVariant = productVariantRepository.findById(inventoryDTO.getProductVariantId())
                .orElseThrow(() -> new CustomSecurityException(
                        "Product Variant not found with ID: " + inventoryDTO.getProductVariantId(), HttpStatus.NOT_FOUND));
        Warehouse warehouse = warehouseRepository.findById(inventoryDTO.getWarehouseId())
                .orElseThrow(() -> new CustomSecurityException(
                        "Warehouse not found with ID: " + inventoryDTO.getWarehouseId(), HttpStatus.NOT_FOUND));

        inventory.setProductVariant(productVariant);
        inventory.setWarehouse(warehouse);

        inventory = inventoryRepository.save(inventory);
        return convertToDTO(inventory);
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) {
        Inventory existingInventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory not found with ID: " + id, HttpStatus.NOT_FOUND));

        // Update associations if new IDs are provided:
        if (inventoryDTO.getProductVariantId() != null) {
            ProductVariant productVariant = productVariantRepository.findById(inventoryDTO.getProductVariantId())
                    .orElseThrow(() -> new CustomSecurityException(
                            "Product Variant not found with ID: " + inventoryDTO.getProductVariantId(), HttpStatus.NOT_FOUND));
            existingInventory.setProductVariant(productVariant);
        }
        if (inventoryDTO.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(inventoryDTO.getWarehouseId())
                    .orElseThrow(() -> new CustomSecurityException(
                            "Warehouse not found with ID: " + inventoryDTO.getWarehouseId(), HttpStatus.NOT_FOUND));
            existingInventory.setWarehouse(warehouse);
        }

        // Update remaining fields:
        existingInventory.setQuantity(inventoryDTO.getQuantity());
        existingInventory.setReorderLevel(inventoryDTO.getReorderLevel());
        existingInventory.setSafetyStock(inventoryDTO.getSafetyStock());

        Inventory updatedInventory = inventoryRepository.save(existingInventory);
        return convertToDTO(updatedInventory);
    }

    @Override
    public void deleteInventory(Long id) {
        if (!inventoryRepository.existsById(id)) {
            throw new CustomSecurityException("Inventory not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        inventoryRepository.deleteById(id);
    }

    @Override
    public List<InventoryDTO> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryDTO getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory not found with ID: " + id, HttpStatus.NOT_FOUND));
        return convertToDTO(inventory);
    }

    private InventoryDTO convertToDTO(Inventory inventory) {
        InventoryDTO dto = new InventoryDTO();
        dto.setId(inventory.getId());
        dto.setQuantity(inventory.getQuantity());
        dto.setReorderLevel(inventory.getReorderLevel());
        dto.setSafetyStock(inventory.getSafetyStock());
        dto.setLastUpdated(inventory.getLastUpdated());
        dto.setProductVariantId(inventory.getProductVariant().getId());
        dto.setWarehouseId(inventory.getWarehouse().getId());
        return dto;
    }
}

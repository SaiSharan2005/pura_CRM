package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.InventoryMovement;
import com.crm.springbootjwtimplementation.domain.ProductVariant;
import com.crm.springbootjwtimplementation.domain.Warehouse;
import com.crm.springbootjwtimplementation.domain.dto.InventoryMovementDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.InventoryMovementRepository;
import com.crm.springbootjwtimplementation.repository.ProductVariantRepository;
import com.crm.springbootjwtimplementation.repository.WarehouseRepository;
import com.crm.springbootjwtimplementation.service.InventoryMovementService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryMovementServiceImpl implements InventoryMovementService {

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public InventoryMovementDTO createInventoryMovement(InventoryMovementDTO inventoryMovementDTO) {
        InventoryMovement movement = new InventoryMovement();
        
        // Lookup and set associations
        ProductVariant productVariant = productVariantRepository.findById(inventoryMovementDTO.getProductVariantId())
                .orElseThrow(() -> new CustomSecurityException(
                        "Product Variant not found with ID: " + inventoryMovementDTO.getProductVariantId(), HttpStatus.NOT_FOUND));
        Warehouse warehouse = warehouseRepository.findById(inventoryMovementDTO.getWarehouseId())
                .orElseThrow(() -> new CustomSecurityException(
                        "Warehouse not found with ID: " + inventoryMovementDTO.getWarehouseId(), HttpStatus.NOT_FOUND));

        movement.setProductVariant(productVariant);
        movement.setWarehouse(warehouse);
        movement.setMovementType(inventoryMovementDTO.getMovementType());
        movement.setQuantity(inventoryMovementDTO.getQuantity());
        movement.setReference(inventoryMovementDTO.getReference());
        movement.setNote(inventoryMovementDTO.getNote());

        movement = inventoryMovementRepository.save(movement);
        return convertToDTO(movement);
    }

    @Override
    public InventoryMovementDTO updateInventoryMovement(Long id, InventoryMovementDTO inventoryMovementDTO) {
        InventoryMovement existingMovement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory Movement not found with ID: " + id, HttpStatus.NOT_FOUND));

        if (inventoryMovementDTO.getProductVariantId() != null) {
            ProductVariant productVariant = productVariantRepository.findById(inventoryMovementDTO.getProductVariantId())
                    .orElseThrow(() -> new CustomSecurityException(
                            "Product Variant not found with ID: " + inventoryMovementDTO.getProductVariantId(), HttpStatus.NOT_FOUND));
            existingMovement.setProductVariant(productVariant);
        }
        if (inventoryMovementDTO.getWarehouseId() != null) {
            Warehouse warehouse = warehouseRepository.findById(inventoryMovementDTO.getWarehouseId())
                    .orElseThrow(() -> new CustomSecurityException(
                            "Warehouse not found with ID: " + inventoryMovementDTO.getWarehouseId(), HttpStatus.NOT_FOUND));
            existingMovement.setWarehouse(warehouse);
        }

        existingMovement.setMovementType(inventoryMovementDTO.getMovementType());
        existingMovement.setQuantity(inventoryMovementDTO.getQuantity());
        existingMovement.setReference(inventoryMovementDTO.getReference());
        existingMovement.setNote(inventoryMovementDTO.getNote());

        InventoryMovement updatedMovement = inventoryMovementRepository.save(existingMovement);
        return convertToDTO(updatedMovement);
    }

    @Override
    public void deleteInventoryMovement(Long id) {
        if (!inventoryMovementRepository.existsById(id)) {
            throw new CustomSecurityException("Inventory Movement not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        inventoryMovementRepository.deleteById(id);
    }

    @Override
    public List<InventoryMovementDTO> getAllInventoryMovements() {
        return inventoryMovementRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryMovementDTO getInventoryMovementById(Long id) {
        InventoryMovement movement = inventoryMovementRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Inventory Movement not found with ID: " + id, HttpStatus.NOT_FOUND));
        return convertToDTO(movement);
    }

    private InventoryMovementDTO convertToDTO(InventoryMovement movement) {
        InventoryMovementDTO dto = new InventoryMovementDTO();
        dto.setId(movement.getId());
        dto.setMovementType(movement.getMovementType());
        dto.setQuantity(movement.getQuantity());
        dto.setMovementDate(movement.getMovementDate());
        dto.setReference(movement.getReference());
        dto.setNote(movement.getNote());
        dto.setProductVariantId(movement.getProductVariant().getId());
        dto.setWarehouseId(movement.getWarehouse().getId());
        return dto;
    }
}

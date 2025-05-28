package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Warehouse;
import com.crm.springbootjwtimplementation.dto.WarehouseDTO;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.WarehouseRepository;
import com.crm.springbootjwtimplementation.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WarehouseServiceImpl implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO) {
        // Map DTO to entity (createdDate is set automatically by @PrePersist)
        Warehouse warehouse = modelMapper.map(warehouseDTO, Warehouse.class);
        warehouse = warehouseRepository.save(warehouse);
        return modelMapper.map(warehouse, WarehouseDTO.class);
    }

    @Override
    public WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO) {
        Warehouse existingWarehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Warehouse not found with ID: " + id, HttpStatus.NOT_FOUND));

        BeanUtils.copyProperties(warehouseDTO, existingWarehouse, getNullPropertyNames(warehouseDTO));
        Warehouse updatedWarehouse = warehouseRepository.save(existingWarehouse);
        return modelMapper.map(updatedWarehouse, WarehouseDTO.class);
    }

    @Override
    public void deleteWarehouse(Long id) {
        if (!warehouseRepository.existsById(id)) {
            throw new CustomSecurityException("Warehouse not found with ID: " + id, HttpStatus.NOT_FOUND);
        }
        warehouseRepository.deleteById(id);
    }

    @Override
    public List<WarehouseDTO> getAllWarehouses() {
        List<Warehouse> warehouses = warehouseRepository.findAll();
        return warehouses.stream()
                .map(warehouse -> modelMapper.map(warehouse, WarehouseDTO.class))
                .toList();
    }

    @Override
    public WarehouseDTO getWarehouseById(Long id) {
        Warehouse warehouse = warehouseRepository.findById(id)
                .orElseThrow(() -> new CustomSecurityException("Warehouse not found with ID: " + id, HttpStatus.NOT_FOUND));
        return modelMapper.map(warehouse, WarehouseDTO.class);
    }

    // Utility method to ignore null properties during copy
    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor descriptor : src.getPropertyDescriptors()) {
            Object value = src.getPropertyValue(descriptor.getName());
            if (value == null) {
                emptyNames.add(descriptor.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
}

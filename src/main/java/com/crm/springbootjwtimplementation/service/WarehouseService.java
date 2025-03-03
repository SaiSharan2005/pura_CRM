package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.WarehouseDTO;
import java.util.List;

public interface WarehouseService {
    WarehouseDTO createWarehouse(WarehouseDTO warehouseDTO);
    WarehouseDTO updateWarehouse(Long id, WarehouseDTO warehouseDTO);
    void deleteWarehouse(Long id);
    List<WarehouseDTO> getAllWarehouses();
    WarehouseDTO getWarehouseById(Long id);
}

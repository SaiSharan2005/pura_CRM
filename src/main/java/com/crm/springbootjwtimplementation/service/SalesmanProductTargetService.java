package com.crm.springbootjwtimplementation.service;

import java.util.List;

import com.crm.springbootjwtimplementation.domain.dto.SalesmanProductTargetRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.SalesmanProductTargetResponseDTO;

public interface SalesmanProductTargetService {
    SalesmanProductTargetResponseDTO createSalesmanProductTarget(SalesmanProductTargetRequestDTO dto, Long salesmanUserId);
    List<SalesmanProductTargetResponseDTO> getAllSalesmanProductTargets();
    List<SalesmanProductTargetResponseDTO> getTargetsByMonth(String monthYear);
    List<SalesmanProductTargetResponseDTO> getCurrentMonthTargetsForSalesman(Long salesmanUserId);
    SalesmanProductTargetResponseDTO updateSalesmanProductTarget(Long id, SalesmanProductTargetRequestDTO dto, Long salesmanUserId);
    void deleteSalesmanProductTarget(Long id, Long salesmanUserId);
}

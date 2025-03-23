package com.crm.springbootjwtimplementation.service;

import java.util.List;

import com.crm.springbootjwtimplementation.domain.dto.TargetRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.TargetResponseDTO;

public interface TargetService {
    TargetResponseDTO createTarget(TargetRequestDTO requestDTO, Long managerUserId);
    List<TargetResponseDTO> getAllTargets();
    List<TargetResponseDTO> getTargetsByMonth(String monthYear);
    List<TargetResponseDTO> getCurrentMonthTargetsForSalesman(Long salesmanId);
    TargetResponseDTO updateTarget(Long id, TargetRequestDTO requestDTO, Long managerUserId);
    void deleteTarget(Long id, Long managerUserId);
}

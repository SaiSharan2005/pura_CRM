package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.TargetRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.TargetResponseDTO;

import java.util.List;

public interface TargetService {
    TargetResponseDTO createTarget(TargetRequestDTO targetRequestDTO);
    TargetResponseDTO updateTarget(Long id, TargetRequestDTO targetRequestDTO);
    void deleteTarget(Long id);
    TargetResponseDTO getTargetById(Long id);
    List<TargetResponseDTO> getAllTargets();
    List<TargetResponseDTO> getTargetsByAssignedTo(Long assignedToId);
}

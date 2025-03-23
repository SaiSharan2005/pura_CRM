package com.crm.springbootjwtimplementation.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.springbootjwtimplementation.domain.ManagerDetails;
import com.crm.springbootjwtimplementation.domain.SalesmanDetails;
import com.crm.springbootjwtimplementation.domain.Target;
import com.crm.springbootjwtimplementation.domain.dto.TargetRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.TargetResponseDTO;
import com.crm.springbootjwtimplementation.repository.ManagerDetailsRepository;
import com.crm.springbootjwtimplementation.repository.SalesmanDetailsRepository;
import com.crm.springbootjwtimplementation.repository.TargetRepository;
import com.crm.springbootjwtimplementation.service.TargetService;

@Service
public class TargetServiceImpl implements TargetService {

    @Autowired
    private TargetRepository targetRepository;
    
    @Autowired
    private ManagerDetailsRepository managerDetailsRepository;
    
    @Autowired
    private SalesmanDetailsRepository salesmanDetailsRepository;

    @Override
    public TargetResponseDTO createTarget(TargetRequestDTO requestDTO, Long managerUserId) {
        // Fetch the ManagerDetails based on authenticated user id (manager)
        ManagerDetails manager = managerDetailsRepository.findByUserId(managerUserId)
                .orElseThrow(() -> new RuntimeException("Manager not found for user id " + managerUserId));
        
        // Fetch SalesmanDetails from request
        SalesmanDetails salesman = salesmanDetailsRepository.findById(requestDTO.getSalesmanId())
                .orElseThrow(() -> new RuntimeException("Salesman not found with id " + requestDTO.getSalesmanId()));

        Target target = new Target();
        target.setDescription(requestDTO.getDescription());
        target.setDeadline(requestDTO.getDeadline());
        target.setMonthlyOutletCount(requestDTO.getMonthlyOutletCount());
        target.setMonthlyTargetAmount(requestDTO.getMonthlyTargetAmount());
        target.setMonthYear(requestDTO.getMonthYear());
        target.setManager(manager);
        target.setSalesman(salesman);
        // isAchieved remains false by default
        
        Target saved = targetRepository.save(target);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<TargetResponseDTO> getAllTargets() {
        return targetRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TargetResponseDTO> getTargetsByMonth(String monthYear) {
        return targetRepository.findByMonthYear(monthYear).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<TargetResponseDTO> getCurrentMonthTargetsForSalesman(Long salesmanId) {
        return targetRepository.findBySalesmanId(salesmanId).stream()
                .filter(t -> t.getMonthYear().equalsIgnoreCase(java.time.LocalDate.now().toString().substring(0,7))) // assuming format "YYYY-MM"
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TargetResponseDTO updateTarget(Long id, TargetRequestDTO requestDTO, Long managerUserId) {
        Target target = targetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Target not found with id " + id));
        
        // Optionally, verify the manager has permission to update.
        if (!target.getManager().getUser().getId().equals(managerUserId)) {
            throw new RuntimeException("Unauthorized update attempt");
        }
        
        // Update provided fields
        if (requestDTO.getDescription() != null) {
            target.setDescription(requestDTO.getDescription());
        }
        if (requestDTO.getDeadline() != null) {
            target.setDeadline(requestDTO.getDeadline());
        }
        if (requestDTO.getMonthlyOutletCount() != 0) {
            target.setMonthlyOutletCount(requestDTO.getMonthlyOutletCount());
        }
        if (requestDTO.getMonthlyTargetAmount() != null) {
            target.setMonthlyTargetAmount(requestDTO.getMonthlyTargetAmount());
        }
        if (requestDTO.getMonthYear() != null) {
            target.setMonthYear(requestDTO.getMonthYear());
        }
        // For salesman update, if provided
        if (requestDTO.getSalesmanId() != null) {
            SalesmanDetails salesman = salesmanDetailsRepository.findById(requestDTO.getSalesmanId())
                    .orElseThrow(() -> new RuntimeException("Salesman not found with id " + requestDTO.getSalesmanId()));
            target.setSalesman(salesman);
        }
        Target updated = targetRepository.save(target);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteTarget(Long id, Long managerUserId) {
        Target target = targetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Target not found with id " + id));
        // Verify that the authenticated manager is the creator
        if (!target.getManager().getUser().getId().equals(managerUserId)) {
            throw new RuntimeException("Unauthorized delete attempt");
        }
        targetRepository.delete(target);
    }
    
    private TargetResponseDTO mapToResponseDTO(Target target) {
        TargetResponseDTO dto = new TargetResponseDTO();
        dto.setId(target.getId());
        dto.setDescription(target.getDescription());
        dto.setDeadline(target.getDeadline());
        dto.setAchieved(target.isAchieved());
        dto.setMonthlyOutletCount(target.getMonthlyOutletCount());
        dto.setMonthlyTargetAmount(target.getMonthlyTargetAmount());
        dto.setMonthYear(target.getMonthYear());
        dto.setCreatedAt(target.getCreatedAt());
        dto.setUpdatedAt(target.getUpdatedAt());
        dto.setManagerId(target.getManager().getId());
        dto.setSalesmanId(target.getSalesman().getId());
        return dto;
    }
}

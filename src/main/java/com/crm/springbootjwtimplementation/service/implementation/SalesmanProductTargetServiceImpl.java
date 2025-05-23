package com.crm.springbootjwtimplementation.service.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.springbootjwtimplementation.domain.ManagerDetails;
import com.crm.springbootjwtimplementation.domain.ProductVariant;
import com.crm.springbootjwtimplementation.domain.SalesmanDetails;
import com.crm.springbootjwtimplementation.domain.SalesmanProductTarget;
import com.crm.springbootjwtimplementation.domain.dto.SalesmanProductTargetRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.SalesmanProductTargetResponseDTO;
import com.crm.springbootjwtimplementation.repository.ManagerDetailsRepository;
import com.crm.springbootjwtimplementation.repository.ProductVariantRepository;
import com.crm.springbootjwtimplementation.repository.SalesmanDetailsRepository;
import com.crm.springbootjwtimplementation.repository.SalesmanProductTargetRepository;
import com.crm.springbootjwtimplementation.service.SalesmanProductTargetService;

@Service
public class SalesmanProductTargetServiceImpl implements SalesmanProductTargetService {

    @Autowired
    private SalesmanProductTargetRepository targetRepository;
    
    @Autowired
    private SalesmanDetailsRepository salesmanDetailsRepository;
    
    @Autowired
    private ManagerDetailsRepository managerDetailsRepository;
    
    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Override
    public SalesmanProductTargetResponseDTO createSalesmanProductTarget(SalesmanProductTargetRequestDTO dto, Long salesmanUserId) {
        // Look up the SalesmanDetails for the authenticated salesman.
        SalesmanDetails salesman = salesmanDetailsRepository.findByUserId(salesmanUserId).get();
        if (salesman == null) {
            throw new RuntimeException("Salesman not found");
        }
                
        // Find the ProductVariant.
        ProductVariant productVariant = productVariantRepository.findById(dto.getProductVariantId())
                .orElseThrow(() -> new RuntimeException("Product variant not found with id " + dto.getProductVariantId()));
        
        SalesmanProductTarget target = new SalesmanProductTarget();
        target.setSalesman(salesman);
        target.setProductVariant(productVariant);
        target.setProductQuantityTarget(dto.getProductQuantityTarget());
        target.setMonthYear(dto.getMonthYear());
        target.setCarriedOverQuantity(dto.getCarriedOverQuantity());
        
        // Optionally, set the manager if provided.
        if (dto.getManagerId() != null) {
            ManagerDetails manager = managerDetailsRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found with id " + dto.getManagerId()));
            target.setManager(manager);
        }
        
        SalesmanProductTarget saved = targetRepository.save(target);
        return mapToResponseDTO(saved);
    }

    @Override
    public List<SalesmanProductTargetResponseDTO> getAllSalesmanProductTargets() {
        return targetRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesmanProductTargetResponseDTO> getTargetsByMonth(String monthYear) {
        return targetRepository.findByMonthYear(monthYear).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SalesmanProductTargetResponseDTO> getCurrentMonthTargetsForSalesman(Long salesmanUserId) {
        // Get the authenticated salesman's details.
        SalesmanDetails salesman = salesmanDetailsRepository.findByUserId(salesmanUserId).get();
        if (salesman == null) {
            throw new RuntimeException("Salesman not found for user id " + salesmanUserId);
        }
        
        String currentMonthYear = LocalDate.now().toString().substring(0, 7); // "YYYY-MM"
        return targetRepository.findBySalesmanId(salesman.getId()).stream()
                .filter(t -> t.getMonthYear().equalsIgnoreCase(currentMonthYear))
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SalesmanProductTargetResponseDTO updateSalesmanProductTarget(Long id, SalesmanProductTargetRequestDTO dto, Long salesmanUserId) {
        SalesmanProductTarget target = targetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Target not found with id " + id));
        
        // Verify the authenticated salesman is the owner.
        if (!target.getSalesman().getUser().getId().equals(salesmanUserId)) {
            throw new RuntimeException("Unauthorized update attempt");
        }
        
        // Update fields if present.
        if (dto.getProductQuantityTarget() != 0) {
            target.setProductQuantityTarget(dto.getProductQuantityTarget());
        }
        if (dto.getMonthYear() != null) {
            target.setMonthYear(dto.getMonthYear());
        }
        if (dto.getCarriedOverQuantity() != null) {
            target.setCarriedOverQuantity(dto.getCarriedOverQuantity());
        }
        if (dto.getProductVariantId() != null) {
            ProductVariant productVariant = productVariantRepository.findById(dto.getProductVariantId())
                    .orElseThrow(() -> new RuntimeException("Product variant not found with id " + dto.getProductVariantId()));
            target.setProductVariant(productVariant);
        }
        if (dto.getManagerId() != null) {
            ManagerDetails manager = managerDetailsRepository.findById(dto.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found with id " + dto.getManagerId()));
            target.setManager(manager);
        }
        SalesmanProductTarget updated = targetRepository.save(target);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteSalesmanProductTarget(Long id, Long salesmanUserId) {
        SalesmanProductTarget target = targetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Target not found with id " + id));
        if (!target.getSalesman().getUser().getId().equals(salesmanUserId)) {
            throw new RuntimeException("Unauthorized delete attempt");
        }
        targetRepository.delete(target);
    }
    
    // Helper mapping method.
    private SalesmanProductTargetResponseDTO mapToResponseDTO(SalesmanProductTarget target) {
        SalesmanProductTargetResponseDTO dto = new SalesmanProductTargetResponseDTO();
        dto.setId(target.getId());
        dto.setSalesmanId(target.getSalesman().getId());
        dto.setProductVariantId(target.getProductVariant().getId());
        dto.setProductQuantityTarget(target.getProductQuantityTarget());
        dto.setMonthYear(target.getMonthYear());
        dto.setCarriedOverQuantity(target.getCarriedOverQuantity());
        dto.setCreatedAt(target.getCreatedAt());
        dto.setUpdatedAt(target.getUpdatedAt());
        dto.setManagerId(target.getManager() != null ? target.getManager().getId() : null);
        return dto;
    }
}

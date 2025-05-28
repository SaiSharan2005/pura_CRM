package com.crm.springbootjwtimplementation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.springbootjwtimplementation.dto.ResponseMessageDTO;
import com.crm.springbootjwtimplementation.dto.SalesmanProductTargetRequestDTO;
import com.crm.springbootjwtimplementation.dto.SalesmanProductTargetResponseDTO;
import com.crm.springbootjwtimplementation.dto.users.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.SalesmanProductTargetService;

@RestController
@RequestMapping("/api/salesman_product_target")
public class SalesmanProductTargetController {

    @Autowired
    private SalesmanProductTargetService targetService;
    
    @Autowired
    private AuthService authService; // Provides the authenticated user info

    // 1. Create a target
    @PostMapping("/create")
    public ResponseMessageDTO createTarget(@RequestBody SalesmanProductTargetRequestDTO requestDTO) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long salesmanUserId = userToken.getId();
        targetService.createSalesmanProductTarget(requestDTO, salesmanUserId);
        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Salesman product target created successfully");
        response.setSuccess(true);
        return response;
    }

    // 2. Get all targets
    @GetMapping("/all")
    public List<SalesmanProductTargetResponseDTO> getAllTargets() {
        return targetService.getAllSalesmanProductTargets();
    }

    // 3. Get targets by month (e.g., /month/2025-03)
    @GetMapping("/month/{monthYear}")
    public List<SalesmanProductTargetResponseDTO> getTargetsByMonth(@PathVariable String monthYear) {
        return targetService.getTargetsByMonth(monthYear);
    }

    // 4. Get current month targets for the authenticated salesman
    @GetMapping("/currentMonth")
    public List<SalesmanProductTargetResponseDTO> getCurrentMonthTargets() {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long salesmanUserId = userToken.getId();
        return targetService.getCurrentMonthTargetsForSalesman(salesmanUserId);
    }

    // 5. Update a target by id
    @PutMapping("/update/{id}")
    public ResponseMessageDTO updateTarget(@PathVariable Long id, @RequestBody SalesmanProductTargetRequestDTO requestDTO) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long salesmanUserId = userToken.getId();
        targetService.updateSalesmanProductTarget(id, requestDTO, salesmanUserId);
        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Salesman product target updated successfully");
        response.setSuccess(true);
        return response;
    }

    // 6. Delete a target by id
    @DeleteMapping("/delete/{id}")
    public ResponseMessageDTO deleteTarget(@PathVariable Long id) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long salesmanUserId = userToken.getId();
        targetService.deleteSalesmanProductTarget(id, salesmanUserId);
        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Salesman product target deleted successfully");
        response.setSuccess(true);
        return response;
    }
    
    // Additional routes (if needed) can be added here.
}

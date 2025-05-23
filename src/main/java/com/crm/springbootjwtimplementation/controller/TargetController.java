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

import com.crm.springbootjwtimplementation.domain.dto.ResponseMessageDTO;
import com.crm.springbootjwtimplementation.domain.dto.TargetRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.TargetResponseDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.TargetService;

@RestController
@RequestMapping("/api/monthly_target")
public class TargetController {

    @Autowired
    private TargetService targetService;
    
    @Autowired
    private AuthService authService; // Assumed service to get authenticated user details

    // 1. Create a target
    @PostMapping("/create")
    public ResponseMessageDTO createTarget(@RequestBody TargetRequestDTO requestDTO) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long managerUserId = userToken.getId();
        targetService.createTarget(requestDTO, managerUserId);
        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Target created successfully");
        response.setSuccess(true);
        return response;
    }

    // 2. Get all targets
    @GetMapping("/all")
    public List<TargetResponseDTO> getAllTargets() {
        return targetService.getAllTargets();
    }

    // 3. Get targets by month
    @GetMapping("/month/{monthYear}")
    public List<TargetResponseDTO> getTargetsByMonth(@PathVariable String monthYear) {
        return targetService.getTargetsByMonth(monthYear);
    }

    // 4. Get current month targets for the salesman
    @GetMapping("/currentMonth")
    public List<TargetResponseDTO> getCurrentMonthTargetsForSalesman() {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        // Assuming the authenticated user is a salesman and we can retrieve his SalesmanDetails id via userToken
        Long salesmanUserId = userToken.getId();
        return targetService.getCurrentMonthTargetsForSalesman(salesmanUserId);
    }

    // 5. Update target by id
    @PutMapping("/update/{id}")
    public ResponseMessageDTO updateTarget(@PathVariable Long id, @RequestBody TargetRequestDTO requestDTO) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long managerUserId = userToken.getId();
        targetService.updateTarget(id, requestDTO, managerUserId);
        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Target updated successfully");
        response.setSuccess(true);
        return response;
    }

    // 6. Delete target by id
    @DeleteMapping("/delete/{id}")
    public ResponseMessageDTO deleteTarget(@PathVariable Long id) {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        Long managerUserId = userToken.getId();
        targetService.deleteTarget(id, managerUserId);
        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Target deleted successfully");
        response.setSuccess(true);
        return response;
    }
    
    // (Optional) Additional routes can be added as needed.
}

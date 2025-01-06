package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.dto.TargetRequestDTO;
import com.crm.springbootjwtimplementation.domain.dto.TargetResponseDTO;
import com.crm.springbootjwtimplementation.service.TargetService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

// import com.crm.springbootjwtimplementation.util.ApiMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/targets")
public class TargetController {

    @Autowired
    private TargetService targetService;

    @PostMapping("/create")
    public ResponseEntity<String> createTarget(@RequestBody TargetRequestDTO targetRequestDTO) {
        targetService.createTarget(targetRequestDTO);
        return ResponseEntity.ok(ApiMessages.TARGET_CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateTarget(@PathVariable Long id, @RequestBody TargetRequestDTO targetRequestDTO) {
        targetService.updateTarget(id, targetRequestDTO);
        return ResponseEntity.ok(ApiMessages.TARGET_UPDATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTarget(@PathVariable Long id) {
        targetService.deleteTarget(id);
        return ResponseEntity.ok(ApiMessages.TARGET_DELETED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TargetResponseDTO> getTargetById(@PathVariable Long id) {
        return ResponseEntity.ok(targetService.getTargetById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TargetResponseDTO>> getAllTargets() {
        return ResponseEntity.ok(targetService.getAllTargets());
    }

    @GetMapping("/assigned-to/{assignedToId}")
    public ResponseEntity<List<TargetResponseDTO>> getTargetsByAssignedTo(@PathVariable Long assignedToId) {
        return ResponseEntity.ok(targetService.getTargetsByAssignedTo(assignedToId));
    }
}

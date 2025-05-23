package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.dto.ResponseMessageDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.ManagerDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.ManagerDetailsResponseDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.TokenResponseDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.ManagerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/managers")
@Validated
public class ManagerDetailsController {

    private final ManagerDetailsService managerService;
    private final AuthService authService;

    @Autowired
    public ManagerDetailsController(
            ManagerDetailsService managerService,
            AuthService authService) {
        this.managerService = managerService;
        this.authService = authService;
    }

    /**
     * Create manager details for the authenticated user.
     * The incoming DTO must contain no userId field (we set it from the token).
     */
    @PostMapping
    public ResponseEntity<ManagerDetailsDTO> create(
            @Valid @RequestBody ManagerDetailsDTO dto) {

        // override userId from logged‑in user
        TokenResponseDTO user = authService.getAuthenticatedUser();
        dto.setUserId(user.getId());

        ManagerDetailsDTO created = managerService.createManagerDetails(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /**
     * Create manager details for the authenticated user by admin.
     * The incoming DTO must contain no userId field (we set it from the token).
     */
    @PostMapping("/{userId}")
    public ResponseEntity<ManagerDetailsDTO> createByUserId(
            @Valid @RequestBody ManagerDetailsDTO dto,
            @PathVariable Long userId) {
        dto.setUserId(userId);
        ManagerDetailsDTO created =
            managerService.createManagerDetails(dto);

        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(created);
    }

    /**
     * List all manager records, paged.
     * GET /api/managers?page=0&size=20
     */
    @GetMapping
    public ResponseEntity<Page<ManagerDetailsResponseDTO>> listAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<ManagerDetailsResponseDTO> dtos = managerService.getAllManagerDetails(page, size);

        return ResponseEntity.ok(dtos);
    }

    /**
     * Get this user’s own manager record.
     * GET /api/managers/me
     */
    @GetMapping("/me")
    public ResponseEntity<ManagerDetailsResponseDTO> getMyDetails() {
        TokenResponseDTO userToken = authService.getAuthenticatedUser();
        ManagerDetailsResponseDTO dto = managerService.getManagerDetailsByUserId(userToken.getId());

        return ResponseEntity.ok(dto);
    }

    /**
     * Get any user’s manager record by userId.
     * GET /api/managers/{userId}
     */
    @GetMapping("/{userId}")
    public ResponseEntity<ManagerDetailsResponseDTO> getByUserId(
            @PathVariable Long userId) {

        ManagerDetailsResponseDTO dto = managerService.getManagerDetailsByUserId(userId);

        return ResponseEntity.ok(dto);
    }

    /**
     * Update this user’s own manager record.
     * PUT /api/managers/me
     */
    @PutMapping("/me")
    public ResponseEntity<ManagerDetailsDTO> updateMyDetails(
            @Valid @RequestBody ManagerDetailsDTO dto) {

        TokenResponseDTO user = authService.getAuthenticatedUser();
        dto.setUserId(user.getId());

        ManagerDetailsDTO updated = managerService.updateManagerDetailsByUserId(user.getId(), dto);

        return ResponseEntity.ok(updated);
    }

    /**
     * Update any user’s manager by userId.
     * PUT /api/managers/{userId}
     */
    @PutMapping("/{userId}")
    public ResponseEntity<ManagerDetailsDTO> updateByUserId(
            @PathVariable Long userId,
            @Valid @RequestBody ManagerDetailsDTO dto) {

        dto.setUserId(userId);
        ManagerDetailsDTO updated = managerService.updateManagerDetailsByUserId(userId, dto);

        return ResponseEntity.ok(updated);
    }

    /**
     * Delete this user’s own manager record.
     * DELETE /api/managers/me
     */
  @DeleteMapping("/me")
    public ResponseEntity<ResponseMessageDTO> deleteMyDetails() {
        TokenResponseDTO user = authService.getAuthenticatedUser();
        managerService.deleteManagerDetailsByUserId(user.getId());

        ResponseMessageDTO resp = new ResponseMessageDTO();
        resp.setMessage("Your manager profile has been deleted");
        resp.setSuccess(true);
        return ResponseEntity.ok(resp);
    }

    /** Delete by userId */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseMessageDTO> deleteByUserId(
            @PathVariable Long userId) {

        managerService.deleteManagerDetailsByUserId(userId);

        ResponseMessageDTO resp = new ResponseMessageDTO();
        resp.setMessage("Manager details for user " + userId + " deleted");
        resp.setSuccess(true);
        return ResponseEntity.ok(resp);
    }
}

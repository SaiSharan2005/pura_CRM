package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.domain.dto.users.LogisticPersonDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.LogisticPersonDetailsResponseDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.TokenResponseDTO;
import com.crm.springbootjwtimplementation.domain.dto.ResponseMessageDTO;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.service.LogisticPersonDetailsService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/logistics")
public class LogisticPersonDetailsController {

    private final LogisticPersonDetailsService service;
    private final AuthService authService;

    @Autowired
    public LogisticPersonDetailsController(
            LogisticPersonDetailsService service,
            AuthService authService
    ) {
        this.service = service;
        this.authService = authService;
    }

    /** Create or POST /api/logistics */
    @PostMapping
    public ResponseEntity<LogisticPersonDetailsDTO> create(
            @Valid @RequestBody LogisticPersonDetailsDTO dto) {

        TokenResponseDTO user = authService.getAuthenticatedUser();
        var created = service.create(user.getId(), dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /** Create by admin /api/logistics/{userId} */
    @PostMapping("/{userId}")
    public ResponseEntity<LogisticPersonDetailsDTO> createByAdmin(
            @PathVariable Long userId,
            @Valid @RequestBody LogisticPersonDetailsDTO dto) {

        var created = service.create(userId, dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }

    /** List (paged) GET /api/logistics?page=0&size=10 */
    @GetMapping
    public ResponseEntity<Page<LogisticPersonDetailsResponseDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        var pageDto = service.list(page, size);
        return ResponseEntity.ok(pageDto);
    }

    /** Get own details GET /api/logistics/me */
    @GetMapping("/me")
    public ResponseEntity<LogisticPersonDetailsResponseDTO> getSelf() {
        TokenResponseDTO user = authService.getAuthenticatedUser();
        var dto = service.getByUserId(user.getId());
        return ResponseEntity.ok(dto);
    }

    /** Get by userId GET /api/logistics/{userId} */
    @GetMapping("/{userId}")
    public ResponseEntity<LogisticPersonDetailsResponseDTO> getByUser(
            @PathVariable Long userId
    ) {
        var dto = service.getByUserId(userId);
        return ResponseEntity.ok(dto);
    }

    /** Update own PUT /api/logistics/me */
    @PutMapping("/me")
    public ResponseEntity<LogisticPersonDetailsDTO> updateSelf(
            @Valid @RequestBody LogisticPersonDetailsDTO dto
    ) {
        TokenResponseDTO user = authService.getAuthenticatedUser();
        var updated = service.update(user.getId(), dto);
        return ResponseEntity.ok(updated);
    }

    /** Update by userId PUT /api/logistics/{userId} */
    @PutMapping("/{userId}")
    public ResponseEntity<LogisticPersonDetailsDTO> updateByUser(
            @PathVariable Long userId,
            @Valid @RequestBody LogisticPersonDetailsDTO dto
    ) {
        var updated = service.update(userId, dto);
        return ResponseEntity.ok(updated);
    }

    /** Delete own DELETE /api/logistics/me */
    @DeleteMapping("/me")
    public ResponseEntity<ResponseMessageDTO> deleteSelf() {
        TokenResponseDTO user = authService.getAuthenticatedUser();
        service.delete(user.getId());

        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Your logistic profile has been deleted successfully");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }

    /** Delete by userId DELETE /api/logistics/{userId} */
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseMessageDTO> deleteByUser(
            @PathVariable Long userId
    ) {
        service.delete(userId);

        ResponseMessageDTO response = new ResponseMessageDTO();
        response.setMessage("Logistic details for user " + userId + " deleted successfully");
        response.setSuccess(true);

        return ResponseEntity.ok(response);
    }
}

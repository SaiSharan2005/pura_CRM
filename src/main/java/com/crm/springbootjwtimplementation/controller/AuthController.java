package com.crm.springbootjwtimplementation.controller;

import com.crm.springbootjwtimplementation.dto.users.*;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.util.Security.AccessToken;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Validated
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AccessToken> register(
        @Valid @RequestBody UserRegisterDto dto) {
        AccessToken token = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(token);
    }

    @PostMapping("/login")
    public ResponseEntity<AccessToken> login(
        @Valid @RequestBody UserLoginDto dto) {
        AccessToken token = authService.login(dto);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/me")
    public ResponseEntity<TokenResponseDTO> me() {
        TokenResponseDTO me = authService.getAuthenticatedUser();
        return ResponseEntity.ok(me);
    }

    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<UserDTO> updateProfilePicture(
        @PathVariable Long userId,
        @RequestParam("file") MultipartFile file) {
        UserDTO updated = authService.updateProfilePicture(userId, file);
        return ResponseEntity.ok(updated);
    }

    /** New: list users paginated */
    @GetMapping("/users")
    public ResponseEntity<Page<UserDTO>> listUsers(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        Page<UserDTO> users = authService.getAllUsers(page, size);
        return ResponseEntity.ok(users);
    }
}

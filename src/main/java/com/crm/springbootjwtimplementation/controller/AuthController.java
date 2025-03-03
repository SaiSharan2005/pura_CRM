package com.crm.springbootjwtimplementation.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.UserDTO;
// import com.crm.springbootjwtimplementation.domain.dto.UserDTO;
import com.crm.springbootjwtimplementation.domain.dto.UserLoginDto;
import com.crm.springbootjwtimplementation.domain.dto.UserRegisterDto;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.util.Security.AccessToken;

@RestController
@RequestMapping("/api/auth")

public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;
    

    @PostMapping("/register")
    public ResponseEntity<AccessToken> register(@RequestBody UserRegisterDto userRegisterDto) {
        AccessToken accessToken = authService.register(userRegisterDto);
        return ResponseEntity.ok(accessToken);

    }

    @PostMapping("/login")
    public ResponseEntity<AccessToken> login(@RequestBody UserLoginDto userLoginDto) {
        AccessToken accessToken = authService.login(userLoginDto);
        return ResponseEntity.ok(accessToken);
    }
    @GetMapping("/me")
    public ResponseEntity<?> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No authenticated user found.");
        }
    
        Object principal = authentication.getPrincipal();
    
        // Assuming you are using your custom User entity directly with Spring Security
        if (principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) principal;
    
            // Fetch the custom User entity using your repository
            Optional<User> customUser = userRepository.findByUsername(springUser.getUsername());
    
            if (customUser.isPresent()) {
                // Map to UserDTO
                UserDTO userDTO = new UserDTO(customUser.get());
                return ResponseEntity.ok(userDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        }
    
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected principal type: " + principal.getClass().getName());
    }
    @PostMapping("/{userId}/profile-picture")
    public ResponseEntity<UserDTO> updateProfilePicture(
            @PathVariable Long userId,
            @RequestParam("file") MultipartFile file) {
        UserDTO updatedUser = authService.updateProfilePicture(userId, file);
        return ResponseEntity.ok(updatedUser);
    }


@GetMapping("/test")
public ResponseEntity<UserDTO> test() {
    Optional<User> dsa = userRepository.findByUsername("salesman");
    return dsa.map(user -> ResponseEntity.ok(new UserDTO(user)))
              .orElse(ResponseEntity.notFound().build());
}
}

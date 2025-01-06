package com.crm.springbootjwtimplementation.service.implementation;

import com.crm.springbootjwtimplementation.domain.Role;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.TokenResponseDTO;
import com.crm.springbootjwtimplementation.domain.dto.UserDTO;
import com.crm.springbootjwtimplementation.domain.dto.UserLoginDto;
import com.crm.springbootjwtimplementation.domain.dto.UserRegisterDto;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.repository.RoleRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;
import com.crm.springbootjwtimplementation.util.Security.AccessToken;
import com.crm.springbootjwtimplementation.util.Security.ITokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {
    @Autowired
    private ITokenProvider tokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AccessToken register(UserRegisterDto userRegisterDto) {
        checkUserExistsWithUserName(userRegisterDto.getUsername());

        User user = new User();
        user.setEmail(userRegisterDto.getEmail());
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setRoles(getRoles(userRegisterDto.getRoles()));

        userRepository.save(user);

        String username = user.getUsername();
        Long userId = user.getId();
        Set<Role> roles = user.getRoles();

        return tokenProvider.createToken(username,userId,roles);
    }



    @Override
    public AccessToken login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            User userData = userRepository.findByUsername(username).get();
            return tokenProvider.createToken(username,userData.getId(),userData.getRoles());

        }catch (AuthenticationException exception) {
            throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS, HttpStatus.BAD_REQUEST);

        }

    }
    @Override
    public TokenResponseDTO getAuthenticatedUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            throw new CustomSecurityException(ApiMessages.BAD_CREDENTIALS,HttpStatus.BAD_REQUEST);
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User springUser = (org.springframework.security.core.userdetails.User) principal;
    
            // Fetch the custom User entity using your repository
            Optional<User> customUser = userRepository.findByUsername(springUser.getUsername());
    
            if (customUser.isPresent()) {
                // Map to UserDTO
                TokenResponseDTO userDTO = new TokenResponseDTO(customUser.get());
                return userDTO;
            } else {
                throw new CustomSecurityException(ApiMessages.USER_NOT_FOUND,HttpStatus.BAD_REQUEST);

                // return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        }
    
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        //         .body("Unexpected principal type: " + principal.getClass().getName());
        throw new CustomSecurityException("Unexpected principal type: " + principal.getClass().getName(),HttpStatus.BAD_REQUEST);

    }



    private void checkUserExistsWithUserName(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new CustomSecurityException(ApiMessages.USER_ALREADY_EXISTS,HttpStatus.BAD_REQUEST);
        }
    }
    private Set<Role> getRoles(String [] roles){
        Set<Role> userRoles = new HashSet<>();
        for(String role : roles) {
            userRoles.add(roleRepository.findByName(role));
        }
        return userRoles;
    }

}

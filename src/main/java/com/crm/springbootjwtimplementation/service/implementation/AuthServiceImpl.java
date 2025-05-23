package com.crm.springbootjwtimplementation.service.implementation;

import java.io.IOException;
import java.util.*;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.crm.springbootjwtimplementation.domain.Role;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.users.*;
import com.crm.springbootjwtimplementation.exceptions.security.CustomSecurityException;
import com.crm.springbootjwtimplementation.mapper.UserMapper;
import com.crm.springbootjwtimplementation.repository.RoleRepository;
import com.crm.springbootjwtimplementation.repository.UserRepository;
import com.crm.springbootjwtimplementation.service.AuthService;
import com.crm.springbootjwtimplementation.util.Constants.ApiMessages;
import com.crm.springbootjwtimplementation.util.Security.AccessToken;
import com.crm.springbootjwtimplementation.util.Security.ITokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.AuthenticationException;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ITokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final Cloudinary cloudinary;
    private final UserMapper userMapper;

    @Override
    public AccessToken register(UserRegisterDto dto) {
        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new CustomSecurityException(
                ApiMessages.USER_ALREADY_EXISTS + dto.getUsername(),
                HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Set<Role> roles = new HashSet<>();
        for (String roleName : dto.getRoles()) {
            Role role = roleRepository.findByName(roleName);
            if (role == null) {
                throw new CustomSecurityException(
                  "Role not found: " + roleName,
                  HttpStatus.NOT_FOUND);
            }
            roles.add(role);
        }
        user.setRoles(roles);

        user = userRepository.save(user);
        return tokenProvider.createToken(
            user.getUsername(), user.getId(), user.getRoles());
    }

    @Override
    public AccessToken login(UserLoginDto dto) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    dto.getUsername(), dto.getPassword()));
            User user = userRepository.findByUsername(dto.getUsername())
                                      .orElseThrow();
            return tokenProvider.createToken(
                user.getUsername(), user.getId(), user.getRoles());
        } catch (AuthenticationException ex) {
            throw new CustomSecurityException(
              ApiMessages.BAD_CREDENTIALS, HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TokenResponseDTO getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder
                                 .getContext()
                                 .getAuthentication();
        if (auth == null ||
            !auth.isAuthenticated() ||
            "anonymousUser".equals(auth.getPrincipal())) {
            throw new CustomSecurityException(
              "No authenticated user", HttpStatus.UNAUTHORIZED);
        }

        String username = ((org.springframework.security.core.userdetails.User)
                               auth.getPrincipal())
                              .getUsername();

        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new CustomSecurityException(
                ApiMessages.USER_NOT_FOUND + username,
                HttpStatus.NOT_FOUND));

        return userMapper.toTokenResponse(user);
    }

    @Override
    public UserDTO updateProfilePicture(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new CustomSecurityException(
                ApiMessages.USER_NOT_FOUND + userId,
                HttpStatus.NOT_FOUND));
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> upload = cloudinary
                .uploader()
                .upload(file.getBytes(), ObjectUtils.emptyMap());
            user.setImageUrl((String) upload.get("secure_url"));
            user = userRepository.save(user);
        } catch (IOException e) {
            throw new CustomSecurityException(
                "Image upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return userMapper.toUserDto(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDTO> getAllUsers(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new CustomSecurityException(
                ApiMessages.INVALID_INPUT_DATA, HttpStatus.BAD_REQUEST);
        }
        Pageable pg = PageRequest.of(page, size, Sort.by("id").ascending());
        Page<User> users = userRepository.findAll(pg);
        return users.map(userMapper::toUserDto);
    }
}

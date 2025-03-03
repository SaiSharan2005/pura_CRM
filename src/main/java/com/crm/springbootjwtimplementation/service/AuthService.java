package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.domain.dto.TokenResponseDTO;
import com.crm.springbootjwtimplementation.domain.dto.UserDTO;
import com.crm.springbootjwtimplementation.domain.dto.UserLoginDto;
import com.crm.springbootjwtimplementation.domain.dto.UserRegisterDto;
import com.crm.springbootjwtimplementation.util.Security.AccessToken;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    AccessToken register(UserRegisterDto userRegisterDto);

    AccessToken login(UserLoginDto userLoginDto);

    TokenResponseDTO getAuthenticatedUser();
    public UserDTO updateProfilePicture(Long userId, MultipartFile file); 

}

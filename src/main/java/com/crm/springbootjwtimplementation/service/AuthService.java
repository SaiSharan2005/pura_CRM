package com.crm.springbootjwtimplementation.service;

import com.crm.springbootjwtimplementation.domain.dto.users.*;
import com.crm.springbootjwtimplementation.util.Security.AccessToken;
import org.springframework.data.domain.Page;

import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    AccessToken register(UserRegisterDto userRegisterDto);
    AccessToken login(UserLoginDto userLoginDto);

    TokenResponseDTO getAuthenticatedUser();
    UserDTO updateProfilePicture(Long userId, MultipartFile file);

    /** New: paginated listing of all users */
    Page<UserDTO> getAllUsers(int page, int size);
}

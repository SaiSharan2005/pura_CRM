package com.crm.springbootjwtimplementation.util.Security.jwt;

import com.crm.springbootjwtimplementation.domain.Role;
import com.crm.springbootjwtimplementation.util.Security.AccessToken;
import com.crm.springbootjwtimplementation.util.Security.SecretKey;

import java.util.Set;

public interface IJwtTokenHelper {
    String generateJwtToken(SecretKey secretKey, String username, Long userId, Set<Role> roles);
String getUsernameFromJwtToken(SecretKey secretKey, AccessToken accessToken);
Long getUserIdFromJwtToken(SecretKey secretKey, AccessToken accessToken); // Add method to extract user ID
boolean validateJwtToken(SecretKey secretKey, AccessToken accessToken);

}

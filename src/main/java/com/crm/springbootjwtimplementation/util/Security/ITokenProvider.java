package com.crm.springbootjwtimplementation.util.Security;

import com.crm.springbootjwtimplementation.domain.Role;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;

public interface ITokenProvider {


    AccessToken createToken(String username, Long userId,Set<Role> roles);
    boolean validateToken(AccessToken accessToken);
    AccessToken getTokenFromHeader(HttpServletRequest httpServletRequest);
    Authentication getAuthentication(AccessToken accessToken);
}

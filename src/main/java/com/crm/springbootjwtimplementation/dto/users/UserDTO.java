// UserDTO.java (for nesting)
package com.crm.springbootjwtimplementation.dto.users;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import com.crm.springbootjwtimplementation.domain.Role;

@Data
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String imageUrl;
    private Set<String> roles;
}

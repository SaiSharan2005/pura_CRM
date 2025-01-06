package com.crm.springbootjwtimplementation.domain.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.crm.springbootjwtimplementation.domain.Role;
import com.crm.springbootjwtimplementation.domain.User;

public class UserDTO {

    private Long id;

    private String username;
    private String email;
    private Set<String> roles;

    public UserDTO() {
        // Default constructor for serialization/deserialization
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRoles().stream()
                         .map(Role::getName) // Assuming Role has a `getName` method
                         .collect(Collectors.toSet());
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}

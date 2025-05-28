package com.crm.springbootjwtimplementation.mapper;

import org.mapstruct.*;
import com.crm.springbootjwtimplementation.domain.User;
import com.crm.springbootjwtimplementation.dto.users.*;
import com.crm.springbootjwtimplementation.domain.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "roles", target = "roles")
    UserDTO toUserDto(User user);

    @Mapping(source = "roles", target = "roles")
    TokenResponseDTO toTokenResponse(User user);

default Set<String> mapRoles(Set<Role> roles) {
    return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
}
}

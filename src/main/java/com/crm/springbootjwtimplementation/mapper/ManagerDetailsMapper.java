package com.crm.springbootjwtimplementation.mapper;

import org.mapstruct.*;
import com.crm.springbootjwtimplementation.domain.ManagerDetails;
import com.crm.springbootjwtimplementation.domain.dto.users.ManagerDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.ManagerDetailsResponseDTO;

@Mapper(componentModel = "spring", uses = UserMapper.class, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ManagerDetailsMapper {

  // entity → create/update DTO
  @Mapping(source = "user.id", target = "userId")
  ManagerDetailsDTO toDto(ManagerDetails entity);

  // Map incoming DTO→Entity (sets manager.user.id)
  @Mapping(source = "userId", target = "user.id")
  ManagerDetails toEntity(ManagerDetailsDTO dto);

  // Map Entity→ResponseDTO
  // - user will be mapped by UserMapper.toUserDto(...)
  ManagerDetailsResponseDTO toResponseDto(ManagerDetails entity);

  // Partial update
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateFromDto(ManagerDetailsDTO dto, @MappingTarget ManagerDetails entity);
}

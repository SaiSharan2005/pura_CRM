// SalesmanDetailsMapper.java
package com.crm.springbootjwtimplementation.mapper;

import org.mapstruct.*;
import com.crm.springbootjwtimplementation.domain.SalesmanDetails;
import com.crm.springbootjwtimplementation.dto.*;
import com.crm.springbootjwtimplementation.dto.users.SalesmanDetailsDTO;
import com.crm.springbootjwtimplementation.dto.users.SalesmanDetailsResponseDTO;

@Mapper(
  componentModel = "spring",
  uses = { UserMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SalesmanDetailsMapper {
  // entity → create/update DTO
  @Mapping(source = "user.id", target = "userId")
  SalesmanDetailsDTO toDto(SalesmanDetails entity);

  // create/update DTO → entity
  @Mapping(source = "userId", target = "user.id")
  SalesmanDetails toEntity(SalesmanDetailsDTO dto);

  // entity → response DTO (with nested UserDTO)
  @Mapping(source = "user", target = "user")
  SalesmanDetailsResponseDTO toResponseDto(SalesmanDetails entity);

  // apply partial updates (ignoring nulls)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateFromDto(SalesmanDetailsDTO dto, @MappingTarget SalesmanDetails entity);
}

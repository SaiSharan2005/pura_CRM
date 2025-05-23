package com.crm.springbootjwtimplementation.mapper;

import org.mapstruct.*;
import com.crm.springbootjwtimplementation.domain.LogisticPersonDetails;
import com.crm.springbootjwtimplementation.domain.dto.users.LogisticPersonDetailsDTO;
import com.crm.springbootjwtimplementation.domain.dto.users.LogisticPersonDetailsResponseDTO;

@Mapper(
  componentModel = "spring",
  uses = { UserMapper.class },
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface LogisticPersonDetailsMapper {

  /** Entity → Create/Update DTO (extracts userId) */
  @Mapping(source = "user.id", target = "userId")
  LogisticPersonDetailsDTO toDto(LogisticPersonDetails entity);

  /** Create/Update DTO → Entity (sets user.id) */
  @Mapping(source = "userId", target = "user.id")
  LogisticPersonDetails toEntity(LogisticPersonDetailsDTO dto);

  /** Entity → Response DTO (nested UserDTO) */
  @Mapping(source = "user", target = "user")
  LogisticPersonDetailsResponseDTO toResponseDto(LogisticPersonDetails entity);

  /** In-place partial update, ignore nulls */
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateFromDto(LogisticPersonDetailsDTO dto,
                     @MappingTarget LogisticPersonDetails entity);
}

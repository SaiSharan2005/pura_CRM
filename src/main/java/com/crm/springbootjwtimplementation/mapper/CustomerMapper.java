package com.crm.springbootjwtimplementation.mapper;

import org.mapstruct.*;
import com.crm.springbootjwtimplementation.domain.Customer;
import com.crm.springbootjwtimplementation.domain.dto.CustomerDTO;

@Mapper(
  componentModel = "spring",
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface CustomerMapper {
    Customer toEntity(CustomerDTO dto);

    CustomerDTO toDto(Customer entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(CustomerDTO dto, @MappingTarget Customer entity);
}

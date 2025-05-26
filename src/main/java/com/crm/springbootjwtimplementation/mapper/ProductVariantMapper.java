// ProductVariantMapper.java
package com.crm.springbootjwtimplementation.mapper;

import org.mapstruct.*;
import com.crm.springbootjwtimplementation.domain.ProductVariant;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductVariantDTO;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductVariantMapper {

  @Mapping(source = "product.id", target = "productId")
  ProductVariantDTO toDto(ProductVariant entity);

  @Mapping(source = "productId", target = "product.id")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  ProductVariant toEntity(ProductVariantDTO dto);

  // ignore imageUrls field here since we handle upload separately
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateFromDto(ProductVariantDTO dto, @MappingTarget ProductVariant entity);
}

// ProductVariantMapper.java
package com.crm.springbootjwtimplementation.mapper;

import java.util.List;
import org.mapstruct.*;
import com.crm.springbootjwtimplementation.domain.ProductVariant;
import com.crm.springbootjwtimplementation.dto.product.ProductVariantDTO;

@Mapper(
  componentModel = "spring",
  nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
  uses = { ProductVariantImageMapper.class }
)
public interface ProductVariantMapper {

  @Mapping(source = "product.id",     target = "productId")
  @Mapping(source = "images",         target = "imageUrls")      // ← map images
  ProductVariantDTO toDto(ProductVariant entity);

  @Mapping(source = "productId",      target = "product.id")
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  ProductVariant toEntity(ProductVariantDTO dto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateFromDto(ProductVariantDTO dto, @MappingTarget ProductVariant entity);

  // (Optional) if you need list‐to‐list mapping:
  List<ProductVariantDTO> toDtoList(List<ProductVariant> entities);
}

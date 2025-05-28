// src/main/java/com/crm/springbootjwtimplementation/mapper/ProductVariantImageMapper.java
package com.crm.springbootjwtimplementation.mapper;

import org.mapstruct.Mapper;
import com.crm.springbootjwtimplementation.domain.ProductVariantImage;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductVariantImageDTO;

@Mapper(componentModel = "spring")
public interface ProductVariantImageMapper {
    ProductVariantImageDTO toDto(ProductVariantImage image);
}

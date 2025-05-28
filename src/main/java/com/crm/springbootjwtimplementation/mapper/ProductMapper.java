package com.crm.springbootjwtimplementation.mapper;

import java.util.List;

import org.mapstruct.*;
import com.crm.springbootjwtimplementation.domain.Product;
import com.crm.springbootjwtimplementation.domain.ProductVariant;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductDTO;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductSummaryDTO;
import com.crm.springbootjwtimplementation.domain.dto.product.ProductVariantDTO;

@Mapper(componentModel = "spring", uses = { ProductVariantMapper.class }, // ← add this
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {
    // @Mapping(source = "id", target = "id")
    // @Mapping(source = "productName", target = "productName")
    // @Mapping(source = "description", target = "description")
    // @Mapping(source = "productStatus", target = "productStatus")
    // @Mapping(source = "createdDate", target = "createdDate")
    // @Mapping(source = "thumbnailUrl", target = "thumbnailUrl")
    @Mapping(source = "variants", target = "variants")
    ProductDTO toDto(Product product);

    // @IterableMapping(elementTargetType = ProductVariantDTO.class)
    // List<ProductVariantDTO> variantsToDto(List<ProductVariant> variants);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", ignore = true) // new entities get no ID from DTO
    @Mapping(source = "productName", target = "productName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "variants", target = "variants")
    Product toEntity(ProductDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(ProductDTO dto, @MappingTarget Product entity);

    ProductSummaryDTO toSummaryDto(Product entity);

}

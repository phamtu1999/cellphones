package com.electronics.modules.product.mapper;

import com.electronics.modules.product.dto.request.BrandRequest;
import com.electronics.modules.product.dto.response.BrandResponse;
import com.electronics.modules.product.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BrandMapper {
    BrandResponse toResponse(Brand brand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Brand toEntity(BrandRequest request);
}

package com.electronics.modules.product.mapper;

import com.electronics.modules.product.dto.request.CategoryRequest;
import com.electronics.modules.product.dto.response.CategoryResponse;
import com.electronics.modules.product.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "parentId", source = "parent.id")
    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Category toEntity(CategoryRequest request);
}

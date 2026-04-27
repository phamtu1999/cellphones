package com.electronics.modules.product.mapper;

import com.electronics.modules.product.dto.request.CategoryRequest;
import com.electronics.modules.product.dto.response.CategoryResponse;
import com.electronics.modules.product.entity.Category;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-27T18:36:03+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryResponse toResponse(Category category) {
        if ( category == null ) {
            return null;
        }

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setParentId( categoryParentId( category ) );
        categoryResponse.setId( category.getId() );
        categoryResponse.setName( category.getName() );
        categoryResponse.setSlug( category.getSlug() );
        categoryResponse.setDescription( category.getDescription() );
        categoryResponse.setImageUrl( category.getImageUrl() );
        categoryResponse.setCreatedAt( category.getCreatedAt() );

        return categoryResponse;
    }

    @Override
    public Category toEntity(CategoryRequest request) {
        if ( request == null ) {
            return null;
        }

        Category.CategoryBuilder category = Category.builder();

        category.name( request.getName() );
        category.slug( request.getSlug() );
        category.description( request.getDescription() );
        category.imageUrl( request.getImageUrl() );

        return category.build();
    }

    private UUID categoryParentId(Category category) {
        if ( category == null ) {
            return null;
        }
        Category parent = category.getParent();
        if ( parent == null ) {
            return null;
        }
        UUID id = parent.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}

package com.electronics.modules.product.mapper;

import com.electronics.modules.product.dto.request.BrandRequest;
import com.electronics.modules.product.dto.response.BrandResponse;
import com.electronics.modules.product.entity.Brand;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-27T18:36:03+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class BrandMapperImpl implements BrandMapper {

    @Override
    public BrandResponse toResponse(Brand brand) {
        if ( brand == null ) {
            return null;
        }

        BrandResponse brandResponse = new BrandResponse();

        brandResponse.setId( brand.getId() );
        brandResponse.setName( brand.getName() );
        brandResponse.setSlug( brand.getSlug() );
        brandResponse.setLogoUrl( brand.getLogoUrl() );
        brandResponse.setCreatedAt( brand.getCreatedAt() );

        return brandResponse;
    }

    @Override
    public Brand toEntity(BrandRequest request) {
        if ( request == null ) {
            return null;
        }

        Brand.BrandBuilder brand = Brand.builder();

        brand.name( request.getName() );
        brand.slug( request.getSlug() );
        brand.logoUrl( request.getLogoUrl() );

        return brand.build();
    }
}

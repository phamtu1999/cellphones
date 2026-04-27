package com.electronics.modules.product.mapper;

import com.electronics.modules.product.dto.request.ProductRequest;
import com.electronics.modules.product.dto.response.ProductResponse;
import com.electronics.modules.product.entity.Brand;
import com.electronics.modules.product.entity.Category;
import com.electronics.modules.product.entity.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-27T18:36:03+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductResponse toResponse(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductResponse productResponse = new ProductResponse();

        productResponse.setBrandId( productBrandId( product ) );
        productResponse.setBrandName( productBrandName( product ) );
        productResponse.setCategoryId( productCategoryId( product ) );
        productResponse.setCategoryName( productCategoryName( product ) );
        productResponse.setId( product.getId() );
        productResponse.setName( product.getName() );
        productResponse.setSlug( product.getSlug() );
        productResponse.setSkuBase( product.getSkuBase() );
        productResponse.setDescription( product.getDescription() );
        productResponse.setBasePrice( product.getBasePrice() );
        productResponse.setThumbnailUrl( product.getThumbnailUrl() );
        List<String> list = product.getImages();
        if ( list != null ) {
            productResponse.setImages( new ArrayList<String>( list ) );
        }
        productResponse.setSpecifications( product.getSpecifications() );
        productResponse.setStatus( product.getStatus() );
        productResponse.setIsFeatured( product.getIsFeatured() );
        productResponse.setCreatedAt( product.getCreatedAt() );
        productResponse.setUpdatedAt( product.getUpdatedAt() );

        return productResponse;
    }

    @Override
    public Product toEntity(ProductRequest request) {
        if ( request == null ) {
            return null;
        }

        Product.ProductBuilder product = Product.builder();

        product.name( request.getName() );
        product.slug( request.getSlug() );
        product.skuBase( request.getSkuBase() );
        product.description( request.getDescription() );
        product.basePrice( request.getBasePrice() );
        product.thumbnailUrl( request.getThumbnailUrl() );
        List<String> list = request.getImages();
        if ( list != null ) {
            product.images( new ArrayList<String>( list ) );
        }
        product.specifications( request.getSpecifications() );
        product.status( request.getStatus() );
        product.isFeatured( request.getIsFeatured() );

        return product.build();
    }

    private UUID productBrandId(Product product) {
        if ( product == null ) {
            return null;
        }
        Brand brand = product.getBrand();
        if ( brand == null ) {
            return null;
        }
        UUID id = brand.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String productBrandName(Product product) {
        if ( product == null ) {
            return null;
        }
        Brand brand = product.getBrand();
        if ( brand == null ) {
            return null;
        }
        String name = brand.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private UUID productCategoryId(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        UUID id = category.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String productCategoryName(Product product) {
        if ( product == null ) {
            return null;
        }
        Category category = product.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}

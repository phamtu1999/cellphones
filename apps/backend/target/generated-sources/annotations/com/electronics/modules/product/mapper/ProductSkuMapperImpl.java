package com.electronics.modules.product.mapper;

import com.electronics.modules.product.dto.request.ProductSkuRequest;
import com.electronics.modules.product.dto.response.ProductSkuResponse;
import com.electronics.modules.product.entity.Product;
import com.electronics.modules.product.entity.ProductSku;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-04-27T18:36:03+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class ProductSkuMapperImpl implements ProductSkuMapper {

    @Override
    public ProductSkuResponse toResponse(ProductSku sku) {
        if ( sku == null ) {
            return null;
        }

        ProductSkuResponse productSkuResponse = new ProductSkuResponse();

        productSkuResponse.setProductId( skuProductId( sku ) );
        productSkuResponse.setProductName( skuProductName( sku ) );
        productSkuResponse.setId( sku.getId() );
        productSkuResponse.setSkuCode( sku.getSkuCode() );
        productSkuResponse.setPriceOverride( sku.getPriceOverride() );
        productSkuResponse.setStockTotal( sku.getStockTotal() );
        productSkuResponse.setAttributeValues( sku.getAttributeValues() );
        productSkuResponse.setCreatedAt( sku.getCreatedAt() );

        return productSkuResponse;
    }

    @Override
    public ProductSku toEntity(ProductSkuRequest request) {
        if ( request == null ) {
            return null;
        }

        ProductSku.ProductSkuBuilder productSku = ProductSku.builder();

        productSku.skuCode( request.getSkuCode() );
        productSku.priceOverride( request.getPriceOverride() );
        productSku.stockTotal( request.getStockTotal() );
        productSku.attributeValues( request.getAttributeValues() );

        return productSku.build();
    }

    private UUID skuProductId(ProductSku productSku) {
        if ( productSku == null ) {
            return null;
        }
        Product product = productSku.getProduct();
        if ( product == null ) {
            return null;
        }
        UUID id = product.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String skuProductName(ProductSku productSku) {
        if ( productSku == null ) {
            return null;
        }
        Product product = productSku.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}

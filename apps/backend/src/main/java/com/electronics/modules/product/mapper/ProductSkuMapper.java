package com.electronics.modules.product.mapper;

import com.electronics.modules.product.dto.request.ProductSkuRequest;
import com.electronics.modules.product.dto.response.ProductSkuResponse;
import com.electronics.modules.product.entity.ProductSku;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductSkuMapper {

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    ProductSkuResponse toResponse(ProductSku sku);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    ProductSku toEntity(ProductSkuRequest request);
}

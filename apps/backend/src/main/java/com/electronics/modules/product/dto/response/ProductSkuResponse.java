package com.electronics.modules.product.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class ProductSkuResponse {
    private UUID id;
    private UUID productId;
    private String productName;
    private String skuCode;
    private BigDecimal priceOverride;
    private Integer stockTotal;
    private JsonNode attributeValues;
    private OffsetDateTime createdAt;
}

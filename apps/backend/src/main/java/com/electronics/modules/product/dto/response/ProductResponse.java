package com.electronics.modules.product.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ProductResponse {
    private UUID id;
    private UUID brandId;
    private String brandName;
    private UUID categoryId;
    private String categoryName;
    private String name;
    private String slug;
    private String skuBase;
    private String description;
    private BigDecimal basePrice;
    private String thumbnailUrl;
    private List<String> images;
    private JsonNode specifications;
    private String status;
    private Boolean isFeatured;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}

package com.electronics.modules.product.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class ProductRequest {
    @NotNull(message = "Brand ID is required")
    private UUID brandId;

    @NotNull(message = "Category ID is required")
    private UUID categoryId;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotBlank(message = "Slug is required")
    private String slug;

    @NotBlank(message = "Base SKU is required")
    private String skuBase;

    private String description;

    @NotNull(message = "Base price is required")
    @PositiveOrZero
    private BigDecimal basePrice;

    private String thumbnailUrl;
    private List<String> images;
    private JsonNode specifications;
    private String status;
    private Boolean isFeatured;
}

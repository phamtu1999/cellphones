package com.electronics.modules.product.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductSkuRequest {
    @NotNull(message = "Product ID is required")
    private UUID productId;

    @NotBlank(message = "SKU Code is required")
    private String skuCode;

    @PositiveOrZero
    private BigDecimal priceOverride;

    @PositiveOrZero
    private Integer stockTotal;

    private JsonNode attributeValues;
}

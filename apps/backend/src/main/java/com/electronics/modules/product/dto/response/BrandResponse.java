package com.electronics.modules.product.dto.response;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class BrandResponse {
    private UUID id;
    private String name;
    private String slug;
    private String logoUrl;
    private OffsetDateTime createdAt;
}

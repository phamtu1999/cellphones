package com.electronics.modules.product.dto.response;

import lombok.Data;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class CategoryResponse {
    private UUID id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
    private UUID parentId;
    private OffsetDateTime createdAt;
}

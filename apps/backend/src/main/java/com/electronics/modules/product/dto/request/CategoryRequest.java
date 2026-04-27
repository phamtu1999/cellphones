package com.electronics.modules.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryRequest {
    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Slug is required")
    @Size(max = 120)
    private String slug;

    private String description;
    private String imageUrl;
    private UUID parentId;
}

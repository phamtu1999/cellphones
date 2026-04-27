package com.electronics.modules.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BrandRequest {
    @NotBlank(message = "Brand name is required")
    @Size(max = 100)
    private String name;

    @NotBlank(message = "Slug is required")
    @Size(max = 120)
    private String slug;

    private String logoUrl;
}

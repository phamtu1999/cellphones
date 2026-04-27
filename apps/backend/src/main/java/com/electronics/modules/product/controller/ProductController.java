package com.electronics.modules.product.controller;

import com.electronics.common.api.ApiResponse;
import com.electronics.modules.product.dto.request.ProductRequest;
import com.electronics.modules.product.dto.response.ProductResponse;
import com.electronics.modules.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ApiResponse<Page<ProductResponse>> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success(productService.getAll(null, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductResponse> getById(@PathVariable UUID id) {
        return ApiResponse.success(productService.getById(id));
    }

    @PostMapping
    public ApiResponse<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return ApiResponse.success(productService.create(request), "Product created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductResponse> update(@PathVariable UUID id, @Valid @RequestBody ProductRequest request) {
        return ApiResponse.success(productService.update(id, request), "Product updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        productService.delete(id);
        return ApiResponse.success(null, "Product deleted successfully");
    }
}

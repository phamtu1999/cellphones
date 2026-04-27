package com.electronics.modules.product.controller;

import com.electronics.common.api.ApiResponse;
import com.electronics.modules.product.dto.request.CategoryRequest;
import com.electronics.modules.product.dto.response.CategoryResponse;
import com.electronics.modules.product.entity.Category;
import com.electronics.modules.product.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ApiResponse<Page<CategoryResponse>> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        // Basic implementation for now, Specification can be added later
        return ApiResponse.success(categoryService.getAll(null, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getById(@PathVariable UUID id) {
        return ApiResponse.success(categoryService.getById(id));
    }

    @PostMapping
    public ApiResponse<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        return ApiResponse.success(categoryService.create(request), "Category created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> update(@PathVariable UUID id, @Valid @RequestBody CategoryRequest request) {
        return ApiResponse.success(categoryService.update(id, request), "Category updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        categoryService.delete(id);
        return ApiResponse.success(null, "Category deleted successfully");
    }
}

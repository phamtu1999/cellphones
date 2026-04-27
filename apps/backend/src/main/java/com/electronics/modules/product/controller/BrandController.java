package com.electronics.modules.product.controller;

import com.electronics.common.api.ApiResponse;
import com.electronics.modules.product.dto.request.BrandRequest;
import com.electronics.modules.product.dto.response.BrandResponse;
import com.electronics.modules.product.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public ApiResponse<Page<BrandResponse>> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success(brandService.getAll(null, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<BrandResponse> getById(@PathVariable UUID id) {
        return ApiResponse.success(brandService.getById(id));
    }

    @PostMapping
    public ApiResponse<BrandResponse> create(@Valid @RequestBody BrandRequest request) {
        return ApiResponse.success(brandService.create(request), "Brand created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<BrandResponse> update(@PathVariable UUID id, @Valid @RequestBody BrandRequest request) {
        return ApiResponse.success(brandService.update(id, request), "Brand updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        brandService.delete(id);
        return ApiResponse.success(null, "Brand deleted successfully");
    }
}

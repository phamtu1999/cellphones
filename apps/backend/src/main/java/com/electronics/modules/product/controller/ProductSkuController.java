package com.electronics.modules.product.controller;

import com.electronics.common.api.ApiResponse;
import com.electronics.modules.product.dto.request.ProductSkuRequest;
import com.electronics.modules.product.dto.response.ProductSkuResponse;
import com.electronics.modules.product.service.ProductSkuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/skus")
@RequiredArgsConstructor
public class ProductSkuController {

    private final ProductSkuService skuService;

    @GetMapping
    public ApiResponse<Page<ProductSkuResponse>> getAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ApiResponse.success(skuService.getAll(null, pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<ProductSkuResponse> getById(@PathVariable UUID id) {
        return ApiResponse.success(skuService.getById(id));
    }

    @PostMapping
    public ApiResponse<ProductSkuResponse> create(@Valid @RequestBody ProductSkuRequest request) {
        return ApiResponse.success(skuService.create(request), "SKU created successfully");
    }

    @PutMapping("/{id}")
    public ApiResponse<ProductSkuResponse> update(@PathVariable UUID id, @Valid @RequestBody ProductSkuRequest request) {
        return ApiResponse.success(skuService.update(id, request), "SKU updated successfully");
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable UUID id) {
        skuService.delete(id);
        return ApiResponse.success(null, "SKU deleted successfully");
    }
}

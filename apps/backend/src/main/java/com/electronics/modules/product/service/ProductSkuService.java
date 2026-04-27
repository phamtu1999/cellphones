package com.electronics.modules.product.service;

import com.electronics.common.exception.ResourceNotFoundException;
import com.electronics.modules.product.dto.request.ProductSkuRequest;
import com.electronics.modules.product.dto.response.ProductSkuResponse;
import com.electronics.modules.product.entity.Product;
import com.electronics.modules.product.entity.ProductSku;
import com.electronics.modules.product.mapper.ProductSkuMapper;
import com.electronics.modules.product.repository.ProductRepository;
import com.electronics.modules.product.repository.ProductSkuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductSkuService {

    private final ProductSkuRepository skuRepository;
    private final ProductRepository productRepository;
    private final ProductSkuMapper skuMapper;

    @Transactional(readOnly = true)
    public Page<ProductSkuResponse> getAll(Specification<ProductSku> spec, Pageable pageable) {
        return skuRepository.findAll(spec, pageable).map(skuMapper::toResponse);
    }

    @Cacheable(value = "skus", key = "#id")
    @Transactional(readOnly = true)
    public ProductSkuResponse getById(UUID id) {
        ProductSku sku = skuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SKU not found with id: " + id));
        return skuMapper.toResponse(sku);
    }

    @Caching(evict = {
        @CacheEvict(value = "skus", allEntries = true),
        @CacheEvict(value = "products", allEntries = true)
    })
    @Transactional
    public ProductSkuResponse create(ProductSkuRequest request) {
        if (skuRepository.existsBySkuCode(request.getSkuCode())) {
            throw new IllegalArgumentException("SKU Code already exists: " + request.getSkuCode());
        }
        
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        ProductSku sku = skuMapper.toEntity(request);
        sku.setProduct(product);
        
        return skuMapper.toResponse(skuRepository.save(sku));
    }

    @CachePut(value = "skus", key = "#id")
    @CacheEvict(value = "skus", allEntries = true)
    @Transactional
    public ProductSkuResponse update(UUID id, ProductSkuRequest request) {
        ProductSku sku = skuRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SKU not found"));
        
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        sku.setSkuCode(request.getSkuCode());
        sku.setPriceOverride(request.getPriceOverride());
        sku.setStockTotal(request.getStockTotal());
        sku.setAttributeValues(request.getAttributeValues());
        sku.setProduct(product);
        
        return skuMapper.toResponse(skuRepository.save(sku));
    }

    @CacheEvict(value = "skus", allEntries = true)
    @Transactional
    public void delete(UUID id) {
        if (!skuRepository.existsById(id)) {
            throw new ResourceNotFoundException("SKU not found");
        }
        skuRepository.deleteById(id);
    }
}

package com.electronics.modules.product.service;

import com.electronics.common.exception.ResourceNotFoundException;
import com.electronics.modules.product.dto.request.ProductRequest;
import com.electronics.modules.product.dto.response.ProductResponse;
import com.electronics.modules.product.entity.Brand;
import com.electronics.modules.product.entity.Category;
import com.electronics.modules.product.entity.Product;
import com.electronics.modules.product.mapper.ProductMapper;
import com.electronics.modules.product.repository.BrandRepository;
import com.electronics.modules.product.repository.CategoryRepository;
import com.electronics.modules.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public Page<ProductResponse> getAll(Specification<Product> spec, Pageable pageable) {
        return productRepository.findAll(spec, pageable).map(productMapper::toResponse);
    }

    @Cacheable(value = "products", key = "#id")
    @Transactional(readOnly = true)
    public ProductResponse getById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        return productMapper.toResponse(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public ProductResponse create(ProductRequest request) {
        if (productRepository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException("Slug already exists: " + request.getSlug());
        }
        if (productRepository.existsBySkuBase(request.getSkuBase())) {
            throw new IllegalArgumentException("SKU Base already exists: " + request.getSkuBase());
        }
        
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Product product = productMapper.toEntity(request);
        product.setBrand(brand);
        product.setCategory(category);
        
        return productMapper.toResponse(productRepository.save(product));
    }

    @CachePut(value = "products", key = "#id")
    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public ProductResponse update(UUID id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        product.setName(request.getName());
        product.setSlug(request.getSlug());
        product.setSkuBase(request.getSkuBase());
        product.setDescription(request.getDescription());
        product.setBasePrice(request.getBasePrice());
        product.setThumbnailUrl(request.getThumbnailUrl());
        product.setImages(request.getImages());
        product.setSpecifications(request.getSpecifications());
        product.setStatus(request.getStatus());
        product.setIsFeatured(request.getIsFeatured());
        product.setBrand(brand);
        product.setCategory(category);
        
        return productMapper.toResponse(productRepository.save(product));
    }

    @CacheEvict(value = "products", allEntries = true)
    @Transactional
    public void delete(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Product not found");
        }
        productRepository.deleteById(id);
    }
}

package com.electronics.modules.product.service;

import com.electronics.common.exception.ResourceNotFoundException;
import com.electronics.modules.product.dto.request.CategoryRequest;
import com.electronics.modules.product.dto.response.CategoryResponse;
import com.electronics.modules.product.entity.Category;
import com.electronics.modules.product.mapper.CategoryMapper;
import com.electronics.modules.product.repository.CategoryRepository;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public Page<CategoryResponse> getAll(Specification<Category> spec, Pageable pageable) {
        return categoryRepository.findAll(spec, pageable).map(categoryMapper::toResponse);
    }

    @Cacheable(value = "categories", key = "#id")
    @Transactional(readOnly = true)
    public CategoryResponse getById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
        return categoryMapper.toResponse(category);
    }

    @CacheEvict(value = "categories", allEntries = true)
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        if (categoryRepository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException("Slug already exists: " + request.getSlug());
        }
        Category category = categoryMapper.toEntity(request);
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParent(parent);
        }
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @CachePut(value = "categories", key = "#id")
    @CacheEvict(value = "categories", allEntries = true)
    @Transactional
    public CategoryResponse update(UUID id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());
        category.setImageUrl(request.getImageUrl());
        
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parent category not found"));
            category.setParent(parent);
        } else {
            category.setParent(null);
        }
        
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @CacheEvict(value = "categories", allEntries = true)
    @Transactional
    public void delete(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found");
        }
        categoryRepository.deleteById(id);
    }
}

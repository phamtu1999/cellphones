package com.electronics.modules.product.service;

import com.electronics.common.exception.ResourceNotFoundException;
import com.electronics.modules.product.dto.request.BrandRequest;
import com.electronics.modules.product.dto.response.BrandResponse;
import com.electronics.modules.product.entity.Brand;
import com.electronics.modules.product.mapper.BrandMapper;
import com.electronics.modules.product.repository.BrandRepository;
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
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Transactional(readOnly = true)
    public Page<BrandResponse> getAll(Specification<Brand> spec, Pageable pageable) {
        return brandRepository.findAll(spec, pageable).map(brandMapper::toResponse);
    }

    @Cacheable(value = "brands", key = "#id")
    @Transactional(readOnly = true)
    public BrandResponse getById(UUID id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found with id: " + id));
        return brandMapper.toResponse(brand);
    }

    @CacheEvict(value = "brands", allEntries = true)
    @Transactional
    public BrandResponse create(BrandRequest request) {
        if (brandRepository.existsBySlug(request.getSlug())) {
            throw new IllegalArgumentException("Slug already exists: " + request.getSlug());
        }
        Brand brand = brandMapper.toEntity(request);
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @CachePut(value = "brands", key = "#id")
    @CacheEvict(value = "brands", allEntries = true)
    @Transactional
    public BrandResponse update(UUID id, BrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand not found"));
        
        brand.setName(request.getName());
        brand.setSlug(request.getSlug());
        brand.setLogoUrl(request.getLogoUrl());
        
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @CacheEvict(value = "brands", allEntries = true)
    @Transactional
    public void delete(UUID id) {
        if (!brandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Brand not found");
        }
        brandRepository.deleteById(id);
    }
}

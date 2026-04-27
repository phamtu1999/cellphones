package com.electronics.modules.product.repository;

import com.electronics.modules.product.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID>, JpaSpecificationExecutor<Brand> {
    Optional<Brand> findBySlug(String slug);
    boolean existsBySlug(String slug);
}

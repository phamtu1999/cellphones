package com.electronics.modules.product.repository;

import com.electronics.modules.product.entity.ProductSku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductSkuRepository extends JpaRepository<ProductSku, UUID>, JpaSpecificationExecutor<ProductSku> {
    Optional<ProductSku> findBySkuCode(String skuCode);
    boolean existsBySkuCode(String skuCode);
    List<ProductSku> findByProductId(UUID productId);
}

package com.cams.inventory.management.repository.product;

import com.cams.inventory.management.entity.product.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for performing database operations on ProductEntity.
 * Extends JpaRepository to provide CRUD operations and custom query methods.
 */
@Repository("productRepositoryV1")
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    /**
     * Retrieves a list of ProductEntity objects based on the provided SKUs.
     *
     * @param sku the list of SKUs to search for
     * @return a list of ProductEntity objects matching the provided SKUs
     */
    List<ProductEntity> findAllBySkuIn(List<String> sku);
}

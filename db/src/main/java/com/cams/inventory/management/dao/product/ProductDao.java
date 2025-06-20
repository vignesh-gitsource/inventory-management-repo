package com.cams.inventory.management.dao.product;

import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.entity.product.ProductEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Data Access Object (DAO) interface for product-related operations.
 */
public interface ProductDao {

    /**
     * Persists a list of products in the database.
     *
     * @param productDtos the list of product DTOs to be created
     * @return the list of created product DTOs
     */
    List<ProductDto> createProduct(List<ProductDto> productDtos);

    /**
     * Retrieves a list of products from the database based on their SKUs.
     *
     * @param sku the list of SKUs to search for
     * @return the list of product DTOs matching the provided SKUs
     */
    List<ProductDto> getProductsBySku(List<String> sku);

    /**
     * Retrieves a list of products that have stock below the specified threshold.
     *
     * @return a list of ProductDto objects representing products with low stock
     */
    List<ProductDto> getAllProducts();

    /**
     * Finds a product by its unique identifier.
     *
     * @param productId the unique identifier of the product
     * @return an Optional containing the ProductEntity if found, or empty if not found
     */
    Optional<ProductEntity> findById(UUID productId);
}

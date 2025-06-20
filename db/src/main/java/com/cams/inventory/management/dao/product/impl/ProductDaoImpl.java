package com.cams.inventory.management.dao.product.impl;

import com.cams.inventory.management.dao.product.ProductDao;
import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.entity.product.ProductEntity;
import com.cams.inventory.management.handler.InventoryManagementDBException;
import com.cams.inventory.management.mapper.ProductMapper;
import com.cams.inventory.management.repository.product.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the `ProductDao` interface for handling product-related database operations.
 * Provides methods to persist and retrieve product data.
 */
@Service("productDaoImplV1")
public class ProductDaoImpl implements ProductDao {

    /**
     * Logger instance for logging messages in the ProductDaoImpl class.
     */
    private static final Logger logger = LoggerFactory.getLogger(ProductDaoImpl.class);

    /**
     * Repository for performing CRUD operations on Product entities.
     */
    private final ProductRepository productRepository;

    /**
     * Mapper for transforming product data between DTOs and entities.
     */
    private final ProductMapper productMapper;

    /**
     * Constructor for `ProductDaoImpl`.
     *
     * @param productRepository the repository for product database operations
     * @param productMapper     the mapper for transforming product data between layers
     */
    public ProductDaoImpl(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    /**
     * Persists a list of products in the database.
     *
     * @param productDtos the list of product DTOs to be created
     * @return the list of created product DTOs
     * @throws InventoryManagementDBException if an exception occurs during the database operation
     */
    @Override
    public List<ProductDto> createProduct(List<ProductDto> productDtos) {

        logger.debug("Creating products in the database: {}", productDtos);
        try {
            // Transform the list of ProductDto to ProductEntity
            List<ProductEntity> productEntities = productMapper.transformProductDtoListToProductEntityList(productDtos);

            // Save the list of ProductEntity to the database
            return productMapper.transformProductEntityListToProductDtoList(productRepository.saveAll(productEntities));
        } catch (Exception exception) {
            throw new InventoryManagementDBException("Exception occurred while creating products" + exception.getMessage());
        }
    }

    /**
     * Retrieves a list of products from the database based on their SKUs.
     *
     * @param skus the list of SKUs to search for
     * @return the list of product DTOs matching the provided SKUs
     * @throws InventoryManagementDBException if an exception occurs during the database operation
     */
    @Override
    public List<ProductDto> getProductsBySku(List<String> skus) {
        logger.debug("Fetching products by SKUs: {}", skus);
        try {
            // Validate that the list of SKUs is not empty
            List<ProductEntity> productEntities = productRepository.findAllBySkuIn(skus);

            // If no products are found, throw a ResourceNotFoundException
            return productMapper.transformProductEntityListToProductDtoList(productEntities);
        } catch (Exception exception) {
            throw new InventoryManagementDBException("Exception occurred while fetching products: " + exception.getMessage());
        }
    }

    /**
     * Retrieves a list of products that have stock below the specified threshold.
     *
     * @return a list of ProductDto objects representing products with low stock
     * @throws InventoryManagementDBException if an exception occurs during the database operation
     */
    @Override
    public List<ProductDto> getAllProducts() {
        logger.debug("Fetching all products from the database");
        try {
            // Fetch all products from the repository
            return productMapper.transformProductEntityListToProductDtoList(productRepository.findAll());
        } catch (Exception e) {
            throw new InventoryManagementDBException("Exception occurred while fetching low stock products: " + e.getMessage());
        }
    }

    /**
     * Finds a product by its unique identifier.
     *
     * @param productId the unique identifier of the product
     * @return an Optional containing the ProductEntity if found, or empty if not found
     */
    @Override
    public Optional<ProductEntity> findById(UUID productId) {
        logger.info("Fetching product by ID: {}", productId);
        try {
            // Fetch the product by its ID from the repository
            return productRepository.findById(productId);
        } catch (Exception e) {
            throw new InventoryManagementDBException("Exception occurred while fetching product by ID: " + e.getMessage());
        }
    }
}

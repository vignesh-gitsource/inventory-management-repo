package com.cams.inventory.management.dao.product;

import com.cams.inventory.management.dao.product.impl.ProductDaoImpl;
import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.entity.product.ProductEntity;
import com.cams.inventory.management.handler.InventoryManagementDBException;
import com.cams.inventory.management.mapper.ProductMapper;
import com.cams.inventory.management.repository.product.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Unit tests for the ProductDaoImpl class, which handles database operations related to products.
 * This class uses Mockito for mocking dependencies and JUnit 5 for testing.
 */
@ExtendWith(SpringExtension.class)
class ProductDaoImplTest {

    /**
     * The implementation of ProductDao being tested.
     */
    @InjectMocks
    ProductDaoImpl productDaoImpl;

    /**
     * Mocked repository for managing ProductEntity persistence.
     */
    @Mock
    ProductRepository productRepository;

    /**
     * Mocked mapper for transforming ProductEntity objects to ProductDto objects and vice versa.
     */
    @Mock
    ProductMapper productMapper;

    /**
     * Sample ProductEntity used in test cases.
     */
    ProductEntity productEntity;

    /**
     * Sample ProductDto used in test cases.
     */
    ProductDto productDto;

    /**
     * Sample UUID for the product.
     */
    UUID productId = UUID.randomUUID();

    /**
     * Sets up the test environment by initializing the sample ProductEntity and ProductDto.
     */
    @BeforeEach
    void setUp() {
        productEntity = new ProductEntity();
        productEntity.setId(productId);
        productEntity.setName("Apple");
        productEntity.setSku("4321098");
        productEntity.setPrice(new BigDecimal("100.00"));

        productDto = new ProductDto();
        productDto.setId(String.valueOf(productId));
        productDto.setName("Apple");
        productDto.setSku("4321098");
        productDto.setPrice(new BigDecimal("100.00"));
    }

    /**
     * Tests the successful retrieval of products by SKU.
     */
    @Test
    void testGetProductsBySku_success() {
        List<String> skuIds = List.of("4321098");

        Mockito.when(productMapper.transformProductEntityListToProductDtoList(List.of(productEntity))).thenReturn(List.of(productDto));
        Mockito.when(productRepository.findAllBySkuIn(skuIds)).thenReturn(List.of(productEntity));
        List<ProductDto> results = productDaoImpl.getProductsBySku(skuIds);
        Assertions.assertNotNull(results);
        Assertions.assertFalse(results.isEmpty());
        Mockito.verify(productMapper, Mockito.times(1))
                .transformProductEntityListToProductDtoList(Mockito.anyList());
    }

    /**
     * Tests the failure scenario when retrieving products by SKU throws an exception.
     */
    @Test
    void testGetProductsBySku_failure() {
        List<String> skuIds = List.of("4321098");

        Mockito.when(productRepository.findAllBySkuIn(skuIds)).thenThrow(new RuntimeException("Database error"));
        Assertions.assertThrows(InventoryManagementDBException.class, () -> {
            productDaoImpl.getProductsBySku(skuIds);
        });
    }

    /**
     * Tests the successful retrieval of low-stock products.
     */
    @Test
    void testGetLowStockProducts_success() {
        Mockito.when(productMapper.transformProductEntityListToProductDtoList(List.of(productEntity))).thenReturn(List.of(productDto));
        Mockito.when(productRepository.findAll()).thenReturn(List.of(productEntity));
        List<ProductDto> results = productDaoImpl.getAllProducts();
        Assertions.assertNotNull(results);
        Assertions.assertFalse(results.isEmpty());
    }

    /**
     * Tests the failure scenario when retrieving low-stock products throws an exception.
     */
    @Test
    void testGetLowStockProducts_failure() {
        Mockito.when(productRepository.findAll()).thenThrow(new RuntimeException("Database error"));
        Assertions.assertThrows(InventoryManagementDBException.class, () -> {
            productDaoImpl.getAllProducts();
        });
    }

    /**
     * Tests the successful retrieval of a product by its ID.
     */
    @Test
    void testFindById_success() {
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));
        Optional<ProductEntity> result = productDaoImpl.findById(productId);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
    }

    /**
     * Tests the failure scenario when retrieving a product by its ID throws an exception.
     */
    @Test
    void testFindById_failure() {
        Mockito.when(productRepository.findById(productId)).thenThrow(new RuntimeException("Database error"));
        Assertions.assertThrows(InventoryManagementDBException.class, () -> {
            productDaoImpl.findById(productId);
        });
    }
}

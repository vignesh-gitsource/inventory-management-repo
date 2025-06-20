package com.cams.inventory.management.product;

import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.request.OrderDetailsRequest;
import com.cams.inventory.management.request.ProductRequest;
import com.cams.inventory.management.response.ApiResponse;
import com.cams.inventory.management.service.product.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Unit tests for the `ProductController` class.
 * This class uses JUnit 5 and Mockito to test the behavior of the `ProductController` methods.
 * It verifies the correctness of the controller's logic and its interaction with the `ProductService`.
 */
@ExtendWith(SpringExtension.class)
class ProductControllerTest {

    /**
     * Injects a mock instance of the `ProductController` for testing purposes.
     */
    @InjectMocks
    ProductController productController;

    /**
     * Mocks the `ProductService` dependency to simulate its behavior during tests.
     */
    @Mock
    ProductService productService;

    /**
     * Test case for the `createProduct` method in `ProductController`.
     * Verifies that the method successfully creates a product when valid inputs are provided.
     */
    @Test
    @DisplayName("Create Product - Success")
    void testCreateProduct_success() {

        // Create a mock product request
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Apple"); // Set product name
        productRequest.setSku("5412897"); // Set product SKU
        productRequest.setStock(10); // Set product stock
        productRequest.setPrice(new BigDecimal("100")); // Set product price

        // Create a mock product DTO to simulate the created product
        ProductDto productDto = new ProductDto();
        productDto.setId(String.valueOf(UUID.randomUUID())); // Set product ID
        productDto.setName("Apple"); // Set product name
        productDto.setSku("5412897"); // Set product SKU
        productDto.setStock(10); // Set product stock
        productDto.setPrice(new BigDecimal("100")); // Set product price

        // Mock the behavior of the productService to return the created product
        Mockito.when(productService.createProduct(List.of(productRequest))).thenReturn(Pair.of(
                List.of(productDto),
                List.of("")
        ));

        // Call the controller method and capture the response
        ResponseEntity<Object> response = productController.createProduct(List.of(productRequest));
        ApiResponse<String, List<ProductDto>> apiResponse = (ApiResponse<String, List<ProductDto>>) response.getBody();

        // Assert that the HTTP status is CREATED
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Assert that the API response is not null
        Assertions.assertNotNull(apiResponse);
        // Assert that the response contains data
        Assertions.assertFalse(ObjectUtils.isEmpty(apiResponse));
        // Assert that the response contains one product
        Assertions.assertEquals(1, apiResponse.getData().size());
        // Assert that the response contains no errors
        Assertions.assertFalse(apiResponse.getErrors().isEmpty());
        // Assert that the product name matches the expected value
        Assertions.assertEquals("Apple", apiResponse.getData().get(0).getName());
    }

    /**
     * Test case for the `createProduct` method in `ProductController`.
     * Verifies that the method handles failure scenarios correctly when the service returns an error.
     */
    @Test
    void testCreateProduct_failure() {

        // Create a mock product request
        ProductRequest productRequest = new ProductRequest();
        productRequest.setName("Apple"); // Set product name
        productRequest.setSku("5412897"); // Set product SKU
        productRequest.setStock(10); // Set product stock
        productRequest.setPrice(new BigDecimal("100")); // Set product price

        // Mock the behavior of the productService to return an error
        Mockito.when(productService.createProduct(List.of(productRequest))).thenReturn(Pair.of(
                Collections.emptyList(),
                List.of("Product with SKU already available")
        ));

        // Call the controller method and capture the response
        ResponseEntity<Object> response = productController.createProduct(List.of(productRequest));
        ApiResponse<String, List<ProductDto>> apiResponse = (ApiResponse<String, List<ProductDto>>) response.getBody();

        // Assert that the HTTP status is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // Assert that the API response is not null
        Assertions.assertNotNull(apiResponse);
        // Assert that the response contains no data
        Assertions.assertFalse(ObjectUtils.isEmpty(apiResponse));
        Assertions.assertTrue(apiResponse.getData().isEmpty());
        // Assert that the response contains error messages
        Assertions.assertFalse(apiResponse.getErrors().isEmpty());
        // Assert that the error message matches the expected value
        Assertions.assertEquals("Product with SKU already available", apiResponse.getErrors().get(0));
    }

    /**
     * Test case for the `getLowStockProducts` method in `ProductController`.
     * Verifies that the method successfully retrieves products with low stock.
     */
    @Test
    @DisplayName("Retrieve products with stock below the specified threshold - Success")
    void testLowStockProducts_success() {

        // Create a mock product DTO to simulate a low-stock product
        ProductDto productDto = new ProductDto();
        productDto.setId(String.valueOf(UUID.randomUUID())); // Set product ID
        productDto.setName("Apple"); // Set product name
        productDto.setSku("5412897"); // Set product SKU
        productDto.setStock(5); // Set product stock
        productDto.setPrice(new BigDecimal("100")); // Set product price

        // Mock the behavior of the productService to return the low-stock product
        Mockito.when(productService.getLowStockProducts(10)).thenReturn(List.of(productDto));

        // Call the controller method and capture the response
        ApiResponse<String, List<ProductDto>> response = productController.getLowStockProducts(10);

        // Assert that the response is not null
        Assertions.assertNotNull(response);
        // Assert that the response contains data
        Assertions.assertFalse(ObjectUtils.isEmpty(response));
        // Assert that the response contains one product
        Assertions.assertEquals(1, response.getData().size());
        // Assert that the response contains no errors
        Assertions.assertNull(response.getErrors());
        // Assert that the product name matches the expected value
        Assertions.assertEquals("Apple", response.getData().get(0).getName());
    }

    /**
     * Test case for the `getLowStockProducts` method in `ProductController`.
     * Verifies that the method handles failure scenarios correctly when no products are found.
     */
    @Test
    @DisplayName("Retrieve products with stock below the specified threshold - Failure")
    void testLowStockProducts_failure() {

        // Mock the behavior of the productService to return an empty list
        Mockito.when(productService.getLowStockProducts(10)).thenReturn(Collections.emptyList());

        // Call the controller method and capture the response
        ApiResponse<String, List<ProductDto>> response = productController.getLowStockProducts(10);

        // Assert that the response is not null
        Assertions.assertNotNull(response);
        // Assert that the response contains no data
        Assertions.assertFalse(ObjectUtils.isEmpty(response));
        Assertions.assertTrue(response.getData().isEmpty());
        // Assert that the response contains error messages
        Assertions.assertFalse(response.getErrors().isEmpty());
        // Assert that the error message matches the expected value
        Assertions.assertEquals("No products found with the given stock threshold", response.getErrors().get(0));
    }

    /**
     * Test case for the `getProductSummaryDetails` method in `ProductController`.
     * Verifies that the method successfully retrieves product summary details.
     */
    @Test
    @DisplayName("Summarize product details based on order details requests - Success")
    void testProductSummaryDetails_success() {

        // Mock the behavior of the productService to return product summary details
        Mockito.when(productService.getProductSummaryDetails(List.of(new OrderDetailsRequest())))
                .thenReturn(new HashMap<>(Map.of(
                        "Apple", new BigDecimal("100.00")
                )));

        // Call the controller method and capture the response
        ApiResponse<String, Map<String, BigDecimal>> response = productController
                .getProductSummaryDetails(List.of(new OrderDetailsRequest()));

        // Assert that the response is not null
        Assertions.assertNotNull(response);
        // Assert that the response contains data
        Assertions.assertFalse(ObjectUtils.isEmpty(response));
        // Assert that the response contains one product summary
        Assertions.assertEquals(1, response.getData().size());
        // Assert that the response contains no errors
        Assertions.assertNull(response.getErrors());
        // Assert that the product price matches the expected value
        Assertions.assertEquals("100.00", response.getData().get("Apple").toString());
    }

    /**
     * Test case for the `getProductSummaryDetails` method in `ProductController`.
     * Verifies that the method handles failure scenarios correctly when no summary details are found.
     */
    @Test
    @DisplayName("Summarize product details based on order details requests - failure")
    void testProductSummaryDetails_failure() {

        // Mock the behavior of the productService to return an empty map
        Mockito.when(productService.getProductSummaryDetails(List.of(new OrderDetailsRequest())))
                .thenReturn(new HashMap<>());

        // Call the controller method and capture the response
        ApiResponse<String, Map<String, BigDecimal>> response = productController
                .getProductSummaryDetails(Collections.emptyList());

        // Assert that the response is not null
        Assertions.assertNotNull(response);
        // Assert that the response contains no data
        Assertions.assertFalse(ObjectUtils.isEmpty(response));
        Assertions.assertNull(response.getData());
        // Assert that the response contains error messages
        Assertions.assertFalse(response.getErrors().isEmpty());
        // Assert that the error message matches the expected value
        Assertions.assertEquals("No product summary found for the given orders", response.getErrors().get(0));
    }
}
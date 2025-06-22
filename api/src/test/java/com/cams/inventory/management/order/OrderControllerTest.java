package com.cams.inventory.management.order;


import com.cams.inventory.management.dto.OrderDto;
import com.cams.inventory.management.entity.constant.OrderStatus;
import com.cams.inventory.management.request.OrderRequest;
import com.cams.inventory.management.request.ProductItemRequest;
import com.cams.inventory.management.response.ApiResponse;
import com.cams.inventory.management.service.order.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Unit tests for the `OrderController` class.
 * This class uses JUnit 5 and Mockito to test the behavior of the `OrderController` methods.
 * It verifies the correctness of the controller's logic and its interaction with the `OrderService`.
 */
@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    /**
     * Injects a mock instance of the `OrderController` for testing purposes.
     */
    @InjectMocks
    private OrderController orderController;

    /**
     * Mocks the `OrderService` dependency to simulate its behavior during tests.
     */
    @Mock
    private OrderService orderService;

    /**
     * Test case for the `updateOrderStatus` method in `OrderController`.
     * Verifies that the method successfully updates the order status when valid inputs are provided.
     */
    @Test
    @DisplayName("Test Update Order Status Success - Verifies successful update of order status")
    void testUpdateOrderStatus_success() {

        // Generate a random UUID for the order ID
        UUID orderId = UUID.randomUUID();
        // Define the new order status to be set
        OrderStatus newOrderStatus = OrderStatus.COMPLETED;

        // Create a mock OrderDto object to simulate the updated order
        OrderDto updatedOrderDto = new OrderDto();
        updatedOrderDto.setId(orderId.toString());
        updatedOrderDto.setStatus(newOrderStatus);

        // Mock the behavior of the orderService to return the updated order
        Mockito.when(orderService.updateOrderStatus(orderId, newOrderStatus))
                .thenReturn(updatedOrderDto);

        // Call the controller method and capture the response
        ApiResponse<String, List<Object>> response = orderController.updateOrderStatus(orderId, newOrderStatus);

        // Assert that the response indicates success
        Assertions.assertTrue(response.isSuccess());
        // Assert that the response contains data
        Assertions.assertFalse(ObjectUtils.isEmpty(response.getData()));
        // Assert that the response contains no errors
        Assertions.assertTrue(ObjectUtils.isEmpty(response.getErrors()));

        // Verify that the orderService's updateOrderStatus method was called exactly once
        Mockito.verify(orderService, Mockito.times(1))
                .updateOrderStatus(orderId, newOrderStatus);
    }

    /**
     * Test case for the `updateOrderStatus` method in `OrderController`.
     * Verifies that the method handles failure scenarios correctly when the service returns null.
     */
    @Test
    @DisplayName("Test Update Order Status Failure - Verifies behavior when service returns null")
    void testUpdateOrderStatus_failure() {

        // Generate a random UUID for the order ID
        UUID orderId = UUID.randomUUID();
        // Define the new order status to be set
        OrderStatus newOrderStatus = OrderStatus.COMPLETED;

        // Create a mock OrderDto object to simulate the updated order (not used in this test)
        OrderDto updatedOrderDto = new OrderDto();
        updatedOrderDto.setId(orderId.toString());
        updatedOrderDto.setStatus(newOrderStatus);

        // Mock the behavior of the orderService to return null, simulating a failure
        Mockito.when(orderService.updateOrderStatus(orderId, newOrderStatus))
                .thenReturn(null);

        // Call the controller method and capture the response
        ApiResponse<String, List<Object>> response = orderController.updateOrderStatus(orderId, newOrderStatus);

        // Assert that the response indicates failure
        Assertions.assertFalse(response.isSuccess());
        // Assert that the response contains no data
        Assertions.assertTrue(ObjectUtils.isEmpty(response.getData()));
        // Assert that the response contains error messages
        Assertions.assertFalse(ObjectUtils.isEmpty(response.getErrors()));
    }

    /**
     * Test case for the `createOrder` method in `OrderController`.
     * Verifies that the method successfully creates an order when valid inputs are provided.
     */
    @Test
    @DisplayName("Test Create Order Success - Verifies successful order creation")
    void testCreateOrder_success() {

        // Create a mock product item request
        ProductItemRequest productItemRequest = new ProductItemRequest();
        productItemRequest.setProductId("12345"); // Set product ID
        productItemRequest.setQuantity(5); // Set quantity

        // Generate a random UUID for the order ID
        UUID orderId = UUID.randomUUID();
        // Define the new order status to be set
        OrderStatus newOrderStatus = OrderStatus.COMPLETED;

        // Create a mock OrderDto object to simulate the created order
        OrderDto createdOrderDto = new OrderDto();
        createdOrderDto.setId(orderId.toString()); // Set order ID
        createdOrderDto.setStatus(newOrderStatus); // Set order status

        // Create an order request with the product item
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderItems(List.of(productItemRequest)); // Add product item to the order request

        // Mock the behavior of the orderService to return the created order
        Mockito.when(orderService.createOrder(orderRequest)).thenReturn(createdOrderDto);

        // Call the controller method and capture the response
        ResponseEntity<Object> response = orderController.createOrder(orderRequest);

        // Extract the API response from the response body
        ApiResponse<String, List<Object>> apiResponse = (ApiResponse<String, List<Object>>) response.getBody();

        // Assert that the HTTP status is CREATED
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Assert that the API response is not null
        Assertions.assertNotNull(apiResponse);
        // Assert that the response indicates success
        Assertions.assertTrue(apiResponse.isSuccess());
        // Assert that the response contains no errors
        Assertions.assertTrue(ObjectUtils.isEmpty(apiResponse.getErrors()));
        // Assert that the response contains data
        Assertions.assertFalse(ObjectUtils.isEmpty(apiResponse.getData()));
    }

    /**
     * Test case for the `createOrder` method in `OrderController`.
     * Verifies that the method handles failure scenarios correctly when the service returns null.
     */
    @Test
    @DisplayName("Test Create Order Failure - Verifies behavior when service returns null")
    void testCreateOrder_failure() {

        // Create a mock product item request
        ProductItemRequest productItemRequest = new ProductItemRequest();
        productItemRequest.setProductId("12345"); // Set product ID
        productItemRequest.setQuantity(5); // Set quantity

        // Create an order request with the product item
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderItems(List.of(productItemRequest)); // Add product item to the order request

        // Mock the behavior of the orderService to return null, simulating a failure
        Mockito.when(orderService.createOrder(orderRequest)).thenReturn(null);

        // Call the controller method and capture the response
        ResponseEntity<Object> response = orderController.createOrder(orderRequest);

        // Extract the API response from the response body
        ApiResponse<String, List<Object>> apiResponse = (ApiResponse<String, List<Object>>) response.getBody();

        // Assert that the API response is not null
        Assertions.assertNotNull(apiResponse);
        // Assert that the response indicates failure
        Assertions.assertFalse(apiResponse.isSuccess());
        // Assert that the HTTP status is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // Assert that the response contains error messages
        Assertions.assertFalse(ObjectUtils.isEmpty(apiResponse.getErrors()));
        // Assert that the response contains no data
        Assertions.assertTrue(ObjectUtils.isEmpty(apiResponse.getData()));
    }


    /**
     * Test case for the `getProductSummaryDetails` method in `ProductController`.
     * Verifies that the method successfully retrieves product summary details.
     */
    @Test
    @DisplayName("Summarize product details based on order details requests - Success")
    void testProductSummaryDetails_success() {

        UUID orderId = UUID.randomUUID();
        // Mock the behavior of the productService to return product summary details
        Mockito.when(orderService.getProductSummaryDetails(orderId))
                .thenReturn(new HashMap<>(Map.of(
                        "Apple", new BigDecimal("100.00")
                )));

        // Call the controller method and capture the response
        ApiResponse<String, Map<String, BigDecimal>> response = orderController
                .getProductSummaryDetails(orderId);

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
        Mockito.when(orderService.getProductSummaryDetails(UUID.randomUUID()))
                .thenReturn(new HashMap<>());

        // Call the controller method and capture the response
        ApiResponse<String, Map<String, BigDecimal>> response = orderController
                .getProductSummaryDetails(UUID.randomUUID());

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

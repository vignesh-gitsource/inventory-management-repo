package com.cams.inventory.management.order;

import com.cams.inventory.management.dto.OrderDto;
import com.cams.inventory.management.entity.constant.OrderStatus;
import com.cams.inventory.management.request.OrderRequest;
import com.cams.inventory.management.response.ApiResponse;
import com.cams.inventory.management.service.order.OrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    /**
     * Service layer dependency for handling order-related operations.
     * This is injected via constructor-based dependency injection.
     */
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Endpoint to create a new order.
     *
     * @param orderRequest the request payload containing order details
     * @return a ResponseEntity containing the API response with the created order details
     */
    @PostMapping("/v1/create-order")
    public ResponseEntity<Object> createOrder(@RequestBody @Valid OrderRequest orderRequest) {

        logger.info("Entered into createOrder method with request: {}", orderRequest);
        // Call the service layer to create the order
        OrderDto createdOrders = orderService.createOrder(orderRequest);

        // Check if the order creation was successful
        boolean hasOrderCreated = !ObjectUtils.isEmpty(createdOrders);

        logger.info("Order creation status: {}", hasOrderCreated);

        // Build the API response with success status, data, and errors
        ApiResponse<String, List<Object>> apiResponse = ApiResponse.<String, List<Object>>builder()
                .success(hasOrderCreated) // Indicate success or failure
                .data(hasOrderCreated ? List.of(createdOrders) : Collections.emptyList()) // Include created order if successful
                .errors(hasOrderCreated ? Collections.emptyList() : List.of("Order creation failed or no products were created")) // Include error message if failed
                .build();

        // Determine the HTTP status based on whether the order was created
        HttpStatus status = hasOrderCreated ? HttpStatus.CREATED : HttpStatus.OK;

        // Return the response entity with the appropriate status and body
        return ResponseEntity.status(status).body(apiResponse);
    }

    /**
     * Endpoint to update the status of an existing order.
     *
     * @param orderId     the unique identifier of the order to be updated
     * @param orderStatus the new status to be set for the order
     * @return an ApiResponse containing the success status, updated order details, or error messages
     */
    @PatchMapping("/v1/update-order/{orderId}/status")
    public ApiResponse<String, List<Object>> updateOrderStatus(@PathVariable("orderId") UUID orderId,
                                                               @RequestParam OrderStatus orderStatus) {

        logger.info("Entered into updateOrderStatus method with orderId: {} and orderStatus: {}", orderId, orderStatus);
        // Call the service layer to update the order status
        OrderDto updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);

        // Check if the order update was successful
        boolean hasOrderUpdated = !ObjectUtils.isEmpty(updatedOrder);

        logger.info("Order update status: {}", hasOrderUpdated);
        // Build and return the API response with success status, data, and errors
        return ApiResponse.<String, List<Object>>builder()
                .success(hasOrderUpdated) // Indicate success or failure
                .data(hasOrderUpdated ? List.of(updatedOrder) : Collections.emptyList()) // Include updated order if successful
                .errors(hasOrderUpdated ? null : List.of("Order not found or could not be updated")) // Include error message if failed
                .build();
    }



    /**
     * Endpoint to retrieve a summary of product details based on the provided order details.
     *
     * @param orderId the orderId to calculate the product summary
     * @return an ApiResponse containing the success status, product summary data, or error messages
     */
    @GetMapping("/v1/product-summary")
    public ApiResponse<String, Map<String, BigDecimal>> getProductSummaryDetails(@RequestParam UUID orderId) {

        logger.info("Retrieving product summary details for orderId {}", orderId);
        // Call the service layer to retrieve the product summary details
        Map<String, BigDecimal> productSummary = orderService.getProductSummaryDetails(orderId);

        logger.info("Product summary retrieval completed. Summary size: {}", productSummary.size());
        // Check if the product summary retrieval was successful
        boolean hasSummaryRetrieved = !productSummary.isEmpty();

        // Build and return the API response with success status, data, and errors
        return ApiResponse.<String, Map<String, BigDecimal>>builder()
                .success(hasSummaryRetrieved) // Indicate success or failure
                .data(hasSummaryRetrieved ? productSummary : null) // Include product summary if successful
                .errors(hasSummaryRetrieved ? null : Collections.singletonList("No product summary found for the given orders")) // Include error message if failed
                .build();
    }
}

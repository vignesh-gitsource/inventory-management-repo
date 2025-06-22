package com.cams.inventory.management.service.order;

import com.cams.inventory.management.dto.OrderDto;
import com.cams.inventory.management.entity.constant.OrderStatus;
import com.cams.inventory.management.request.OrderRequest;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * Service interface for managing orders.
 */
public interface OrderService {

    /**
     * Creates a new order based on the provided order request.
     *
     * @param orderRequest the request containing order details
     * @return the created order as a DTO
     */
    OrderDto createOrder(OrderRequest orderRequest);

    /**
     * Updates the status of an existing order.
     *
     * @param orderId     the unique identifier of the order
     * @param orderStatus the new status to set for the order
     * @return the updated order as a DTO
     */
    OrderDto updateOrderStatus(UUID orderId, OrderStatus orderStatus);


    /**
     * Retrieves a summary of product details based on the provided order details requests.
     *
     * @param orderId orderId to calculate the product summary.
     * @return A map where the key is the product identifier and the value is the total amount for that product.
     */
    Map<String, BigDecimal> getProductSummaryDetails(UUID orderId);
}

package com.cams.inventory.management.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request object representing the details of an order item.
 * Contains the product ID, quantity, and product details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDetailsRequest {

    /**
     * The unique identifier for the product.
     */
    private String productId;

    /**
     * The quantity of the product in the order.
     */
    private Integer quantity;

    /**
     * The details of the product.
     */
    private ProductDetailsRequest product;
}

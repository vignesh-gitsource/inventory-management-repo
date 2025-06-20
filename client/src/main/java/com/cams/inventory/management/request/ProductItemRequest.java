package com.cams.inventory.management.request;

import lombok.Data;

/**
 * Request object representing a product item in an order.
 * Contains the product ID and the quantity of the product.
 */
@Data
public class ProductItemRequest {

    /**
     * The unique identifier for the product.
     */
    private String productId;

    /**
     * The quantity of the product in the order.
     */
    private Integer quantity;
}

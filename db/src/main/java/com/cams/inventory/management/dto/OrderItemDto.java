package com.cams.inventory.management.dto;

import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing an item in an order.
 * Contains the product ID, quantity, and associated product details.
 */
@Data
public class OrderItemDto {

    /**
     * The unique identifier of the product.
     */
    private String productId;

    /**
     * The quantity of the product in the order.
     */
    private Integer quantity;

    /**
     * The details of the product associated with this order item.
     */
    private ProductDto product;
}
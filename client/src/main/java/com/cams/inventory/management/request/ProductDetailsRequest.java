package com.cams.inventory.management.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request object representing the details of a product.
 * Contains the product ID, name, SKU, price, and stock information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsRequest {

    /**
     * The unique identifier for the product.
     */
    private String id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * The stock keeping unit (SKU) of the product.
     */
    private String sku;

    /**
     * The price of the product.
     */
    private BigDecimal price;

    /**
     * The available stock of the product.
     */
    private Integer stock;
}
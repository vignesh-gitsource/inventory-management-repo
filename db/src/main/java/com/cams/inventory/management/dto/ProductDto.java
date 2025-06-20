package com.cams.inventory.management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for representing a product.
 * Contains the product ID, name, SKU, price, and stock quantity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    /**
     * The unique identifier of the product.
     */
    private String id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * The Stock Keeping Unit (SKU) of the product.
     */
    private String sku;

    /**
     * The price of the product.
     */
    private BigDecimal price;

    /**
     * The stock quantity of the product.
     */
    private Integer stock;
}

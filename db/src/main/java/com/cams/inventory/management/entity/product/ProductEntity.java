package com.cams.inventory.management.entity.product;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entity representing a product in the inventory system.
 * Each product has a unique identifier, name, SKU, price, version, and stock quantity.
 */
@Data
@Entity
public class ProductEntity {

    /**
     * Unique identifier for the product.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Name of the product.
     * This field is mandatory.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Stock Keeping Unit (SKU) of the product.
     * This field is unique and mandatory.
     */
    @Column(unique = true, nullable = false)
    private String sku;

    /**
     * Price of the product.
     */
    private BigDecimal price;

    /**
     * Version of the product entity, used for optimistic locking.
     */
    @Version
    private Integer version;

    /**
     * Quantity of the product available in stock.
     * This field is mandatory.
     */
    @Column(nullable = false)
    private Integer stock;
}
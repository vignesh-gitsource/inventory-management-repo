package com.cams.inventory.management.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Request object representing a product.
 * Contains the product name, SKU, price, and stock information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    /**
     * The name of the product.
     * Cannot be null or blank.
     */
    @NotNull(message = "Mandatory attribute name is missing")
    @NotBlank(message = "Name cannot be empty")
    private String name;

    /**
     * The stock keeping unit (SKU) of the product.
     * Cannot be empty.
     */
    @NotEmpty(message = "Sku cannot be empty")
    private String sku;

    /**
     * The price of the product.
     */
    private BigDecimal price;

    /**
     * The available stock of the product.
     * Must be at least 1.
     */
    @NotNull(message = "Mandatory stock quantity is missing")
    @Min(1)
    private Integer stock;
}

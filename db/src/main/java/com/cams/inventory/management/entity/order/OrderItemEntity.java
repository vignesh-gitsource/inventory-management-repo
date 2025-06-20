package com.cams.inventory.management.entity.order;

import com.cams.inventory.management.entity.product.ProductEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

/**
 * Entity representing an item in an order.
 * Each item is associated with a product, an order, and a quantity.
 */
@Entity
@Data
public class OrderItemEntity {

    /**
     * Unique identifier for the order item.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * The product associated with this order item.
     */
    @ManyToOne
    private ProductEntity product;

    /**
     * The order to which this item belongs.
     */
    @ManyToOne
    private OrderEntity order;

    /**
     * The quantity of the product in this order item.
     */
    private Integer quantity;
}

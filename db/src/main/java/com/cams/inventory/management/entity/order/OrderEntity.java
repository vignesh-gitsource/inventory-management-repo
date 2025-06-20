package com.cams.inventory.management.entity.order;

import com.cams.inventory.management.entity.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity representing an order in the system.
 * Each order has a unique identifier, a status, and a list of associated items.
 */
@Data
@Entity
public class OrderEntity {

    /**
     * Unique identifier for the order.
     */
    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Status of the order, represented as an enum.
     * Defaults to PENDING.
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    /**
     * List of items associated with the order.
     * The relationship is one-to-many, and changes to the order cascade to its items.
     */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items = new ArrayList<>();
}

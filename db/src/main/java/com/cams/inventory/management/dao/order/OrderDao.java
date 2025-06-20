package com.cams.inventory.management.dao.order;

import com.cams.inventory.management.entity.order.OrderEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * DAO interface for managing orders in the database.
 */
public interface OrderDao {

    /**
     * Creates a new order in the database.
     *
     * @param orderEntity The order entity to be created.
     * @return The created order entity.
     */
    OrderEntity createOrder(OrderEntity orderEntity);

    /**
     * Retrieves the details of an order by its ID.
     *
     * @param orderId The unique identifier of the order.
     * @return An Optional containing the order entity if found, or empty if not found.
     */
    Optional<OrderEntity> getOrderDetails(UUID orderId);
}

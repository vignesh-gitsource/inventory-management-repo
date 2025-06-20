package com.cams.inventory.management.dao.order.impl;

import com.cams.inventory.management.dao.order.OrderDao;
import com.cams.inventory.management.entity.order.OrderEntity;
import com.cams.inventory.management.handler.InventoryManagementDBException;
import com.cams.inventory.management.repository.order.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Implementation of the OrderDao interface for managing orders in the database.
 * Provides methods to create and retrieve order details.
 */
@Service("orderDaoImplV1")
public class OrderDaoImpl implements OrderDao {

    /**
     * Logger instance for logging messages in the OrderDaoImpl class.
     */
    private static final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);

    /**
     * Repository for performing CRUD operations on Order entities.
     */
    private final OrderRepository orderRepository;

    public OrderDaoImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Creates a new order in the database.
     *
     * @param orderEntity The order entity to be created.
     * @return The created order entity.
     */
    @Override
    public OrderEntity createOrder(OrderEntity orderEntity) {
        logger.debug("Creating order in database: {}", orderEntity);
        try {
            // Save order to db.
            return orderRepository.save(orderEntity);
        } catch (Exception ex) {
            throw new InventoryManagementDBException("Error while creating order in database" + ex.getMessage());
        }
    }

    /**
     * Retrieves the details of an order by its ID.
     *
     * @param orderId The unique identifier of the order.
     * @return An Optional containing the order entity if found, or empty if not found.
     */
    @Override
    public Optional<OrderEntity> getOrderDetails(UUID orderId) {
        logger.debug("Fetching order details for orderId: {}", orderId);
        try {
            //Fetch order details for given id.
            return orderRepository.findById(orderId);
        } catch (Exception ex) {
            throw new InventoryManagementDBException("Error while fetching order details for orderId: " + orderId + " - " + ex.getMessage());
        }
    }
}

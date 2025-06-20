package com.cams.inventory.management.service.order.impl;

import com.cams.inventory.management.dao.order.OrderDao;
import com.cams.inventory.management.dao.product.ProductDao;
import com.cams.inventory.management.dto.OrderDto;
import com.cams.inventory.management.entity.constant.OrderStatus;
import com.cams.inventory.management.entity.order.OrderEntity;
import com.cams.inventory.management.entity.order.OrderItemEntity;
import com.cams.inventory.management.entity.product.ProductEntity;
import com.cams.inventory.management.handler.InsufficientStockException;
import com.cams.inventory.management.handler.ResourceNotFoundException;
import com.cams.inventory.management.mapper.OrderMapper;
import com.cams.inventory.management.request.ProductItemRequest;
import com.cams.inventory.management.request.OrderRequest;
import com.cams.inventory.management.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Implementation of the OrderService interface for managing orders.
 * This class provides methods to create orders and update their status.
 * It uses ProductDao, OrderDao, and OrderMapper for database operations
 * and entity-to-DTO transformations.
 */
@Service("orderServiceImplV1")
public class OrderServiceImpl implements OrderService {

    Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    /**
     * ProductDao for managing product-related database operations.
     */
    private final ProductDao productDao;

    /**
     * OrderDao for managing order-related database operations.
     */
    private final OrderDao orderDao;

    /**
     * OrderMapper for transforming OrderEntity objects to OrderDto objects and vice versa.
     */
    private final OrderMapper orderMapper;


    /**
     * Constructs an instance of OrderServiceImpl with the specified dependencies.
     *
     * @param productDao  the ProductDao for managing product-related database operations
     * @param orderDao    the OrderDao for managing order-related database operations
     * @param orderMapper the OrderMapper for transforming OrderEntity objects to OrderDto objects and vice versa
     */
    public OrderServiceImpl(ProductDao productDao,
                            OrderDao orderDao,
                            OrderMapper orderMapper) {
        this.productDao = productDao;
        this.orderDao = orderDao;
        this.orderMapper = orderMapper;
    }

    /**
     * Creates a new order based on the provided OrderRequest.
     * This method processes each order item, checks stock availability,
     * updates product stock, and saves the order with its items.
     *
     * @param orderRequest the request containing order details
     * @return the created OrderDto
     * @throws InsufficientStockException if there is not enough stock for any product in the order
     */
    @Override
    @Transactional
    public OrderDto createOrder(OrderRequest orderRequest) {

        logger.info("Creating order with request: {}", orderRequest);
        // Create new OrderEntity (managed entity)
        OrderEntity orderEntity = new OrderEntity();

        //For each order items
        for (ProductItemRequest orderItem : orderRequest.getOrderItems()) {

            // Load the managed ProductEntity from DB by ID (UUID)
            UUID productId = UUID.fromString(orderItem.getProductId());
            ProductEntity productEntity = productDao.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

            logger.info("Retrieved product: {} with stock: {}",
                    productEntity.getName(), productEntity.getStock());
            // Check stock availability
            if (productEntity.getStock() < orderItem.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for product: " + productEntity.getName());
            }

            // Update stock on the managed entity
            productEntity.setStock(productEntity.getStock() - orderItem.getQuantity());

            logger.info("Updating stock for product: {}. New stock: {}",
                    productEntity.getName(), productEntity.getStock());

            // Create OrderItemEntity and associate with order and product (both managed entities)
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setProduct(productEntity);
            orderItemEntity.setQuantity(orderItem.getQuantity());
            orderItemEntity.setOrder(orderEntity);

            // Add the order item to order
            orderEntity.getItems().add(orderItemEntity);
        }

        // Save orderEntity, cascading will save items
        OrderEntity savedOrder = orderDao.createOrder(orderEntity);

        logger.debug("Order created successfully with ID: {}", savedOrder.getId());
        // Convert saved entity to DTO and return
        return orderMapper.transformOrderEntityToOrderDto(savedOrder);
    }

    /**
     * Updates the status of an existing order.
     * This method retrieves the order by ID, updates its status,
     * and saves the updated order back to the database.
     *
     * @param orderId     the ID of the order to update
     * @param orderStatus the new status to set for the order
     * @return the updated OrderDto, or null if the order does not exist
     */
    @Override
    public OrderDto updateOrderStatus(UUID orderId, OrderStatus orderStatus) {

        logger.debug("Updating order status for orderId: {} to status: {}", orderId, orderStatus);
        // Updated the order status for given orderId
        return orderDao.getOrderDetails(orderId).map(existingOrder -> {
                    existingOrder.setStatus(orderStatus);
                    OrderEntity updatedOrderEntity = orderDao.createOrder(existingOrder);
                    return orderMapper.transformOrderEntityToOrderDto(updatedOrderEntity);
                })
                .orElse(null);
    }
}

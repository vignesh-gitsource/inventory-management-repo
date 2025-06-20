package com.cams.inventory.management.dao.order;

import com.cams.inventory.management.dao.order.impl.OrderDaoImpl;
import com.cams.inventory.management.entity.constant.OrderStatus;
import com.cams.inventory.management.entity.order.OrderEntity;
import com.cams.inventory.management.handler.InventoryManagementDBException;
import com.cams.inventory.management.repository.order.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

/**
 * Unit tests for the OrderDaoImpl class, which handles database operations related to orders.
 * This class uses Mockito for mocking dependencies and JUnit 5 for testing.
 */
@ExtendWith(SpringExtension.class)
class OrderDaoImplTest {

    /**
     * The implementation of OrderDao being tested.
     */
    @InjectMocks
    OrderDaoImpl orderDaoImpl;

    /**
     * Mocked repository for managing OrderEntity persistence.
     */
    @Mock
    OrderRepository orderRepository;

    /**
     * Sample OrderEntity used in test cases.
     */
    OrderEntity orderEntity;

    /**
     * Sets up the test environment by initializing the sample OrderEntity.
     */
    @BeforeEach
    void setUp() {
        orderEntity = new OrderEntity();
        orderEntity.setId(UUID.randomUUID());
        orderEntity.setStatus(OrderStatus.COMPLETED);
    }

    /**
     * Tests the successful creation of an order.
     */
    @Test
    void testCreateOrder_success() {
        Mockito.when(orderRepository.save(orderEntity)).thenReturn(orderEntity);
        OrderEntity result = orderDaoImpl.createOrder(orderEntity);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(OrderStatus.COMPLETED, result.getStatus());
    }

    /**
     * Tests the failure scenario when creating an order throws an exception.
     */
    @Test
    void testCreateOrder_failure() {
        Mockito.when(orderRepository.save(orderEntity)).thenThrow(new RuntimeException("Error while saving order"));
        Assertions.assertThrows(InventoryManagementDBException.class, () ->
                orderDaoImpl.createOrder(orderEntity));
    }

    /**
     * Tests the successful retrieval of order details by ID.
     */
    @Test
    void testGetOrderDetails_success() {
        UUID orderId = UUID.randomUUID();
        Mockito.when(orderRepository.findById(orderId)).thenReturn(Optional.of(orderEntity));
        Optional<OrderEntity> result = orderDaoImpl.getOrderDetails(orderId);
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isPresent());
    }

    /**
     * Tests the failure scenario when retrieving order details throws an exception.
     */
    @Test
    void testGetOrderDetails_failure() {
        UUID orderId = UUID.randomUUID();
        Mockito.when(orderRepository.findById(orderId)).thenThrow(new RuntimeException("Error while saving order"));
        Assertions.assertThrows(InventoryManagementDBException.class, () ->
                orderDaoImpl.getOrderDetails(orderId));
    }
}
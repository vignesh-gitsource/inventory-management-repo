package com.cams.inventory.management.order;

import com.cams.inventory.management.dao.order.OrderDao;
import com.cams.inventory.management.dto.OrderDto;
import com.cams.inventory.management.entity.constant.OrderStatus;
import com.cams.inventory.management.entity.order.OrderEntity;
import com.cams.inventory.management.mapper.OrderMapper;
import com.cams.inventory.management.service.order.impl.OrderServiceImpl;
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
 * OrderServiceImplTest
 * This class contains unit tests for the OrderServiceImpl class.
 * It tests the updateOrderStatus method for both success and failure scenarios.
 */
@ExtendWith(SpringExtension.class)
class OrderServiceImplTest {

    /**
     * Injects the OrderServiceImpl instance and mocks the dependencies.
     */
    @InjectMocks
    OrderServiceImpl orderServiceImpl;

    /**
     * Mocks the OrderDao and OrderMapper dependencies.
     * These mocks will be used to simulate the behavior of the actual DAO and Mapper classes.
     */
    @Mock
    OrderDao orderDao;

    /**
     * Mocks the OrderMapper dependency.
     * This mock will be used to simulate the behavior of the actual Mapper class.
     */
    @Mock
    OrderMapper orderMapper;

    /**
     * The OrderEntity and OrderDto objects used in the tests.
     * These objects will be initialized in the setUp method before each test.
     */
    OrderEntity orderEntity;

    /**
     * The OrderDto object used in the tests.
     * This object will be initialized in the setUp method before each test.
     */
    OrderDto orderDto;

    /**
     * The UUID representing the order ID.
     * This ID will be used to identify the order in the tests.
     */
    UUID orderId = UUID.randomUUID();

    /**
     * Sets up the test environment before each test.
     * Initializes the OrderEntity and OrderDto objects with default values.
     * This method is called before each test to ensure a clean state.
     */
    @BeforeEach
    void setUp() {

        orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        orderEntity.setStatus(OrderStatus.PENDING);

        orderDto = new OrderDto();
        orderDto.setId(orderId.toString());
        orderDto.setStatus(OrderStatus.COMPLETED);
    }

    /**
     * Tests the updateOrderStatus method for a successful update.
     * It mocks the behavior of the DAO and Mapper to return the expected results.
     * Asserts that the returned OrderDto is not null and has the expected status.
     */
    @Test
    void testUpdateOrderStatus_success() {

        Mockito.when(orderDao.getOrderDetails(orderId)).thenReturn(Optional.of(orderEntity));
        Mockito.when(orderDao.createOrder(orderEntity)).thenReturn(orderEntity);
        Mockito.when(orderMapper.transformOrderEntityToOrderDto(orderEntity)).thenReturn(orderDto);

        OrderDto result = orderServiceImpl.updateOrderStatus(orderId, OrderStatus.COMPLETED);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(OrderStatus.COMPLETED, result.getStatus());
    }

    /**
     * Tests the updateOrderStatus method for a failure scenario.
     * It mocks the behavior of the DAO to return an empty Optional, simulating a case where the order does not exist.
     * Asserts that the returned OrderDto is null and no interactions with the DAO or Mapper occur.
     */
    @Test
    void testUpdateOrderStatus_failure() {

        Mockito.when(orderDao.getOrderDetails(orderId)).thenReturn(Optional.empty());

        OrderDto result = orderServiceImpl.updateOrderStatus(orderId, OrderStatus.COMPLETED);

        Assertions.assertNull(result);
        Mockito.verify(orderDao, Mockito.times(0)).createOrder(orderEntity);
        Mockito.verify(orderMapper, Mockito.times(0)).transformOrderEntityToOrderDto(Mockito.any());
    }
}

package com.cams.inventory.management;

import com.cams.inventory.management.dto.OrderDto;
import com.cams.inventory.management.entity.order.OrderEntity;
import com.cams.inventory.management.entity.product.ProductEntity;
import com.cams.inventory.management.handler.InsufficientStockException;
import com.cams.inventory.management.repository.order.OrderRepository;
import com.cams.inventory.management.repository.product.ProductRepository;
import com.cams.inventory.management.request.OrderRequest;
import com.cams.inventory.management.request.ProductItemRequest;
import com.cams.inventory.management.service.order.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Integration tests for order transactions, ensuring proper stock updates and rollback behavior.
 */
@SpringBootTest(classes = InventoryManagementApplication.class)
@Transactional
class OrderTransactionalTest {
    /**
     * Service for handling order-related operations.
     */
    @Autowired
    private OrderService orderService;

    /**
     * Repository for performing CRUD operations on products.
     */
    @Autowired
    private ProductRepository productRepository;

    /**
     * Repository for performing CRUD operations on orders.
     */
    @Autowired
    private OrderRepository orderRepository;

    /**
     * Test case for successfully creating an order and verifying stock updates.
     * Ensures that the stock is reduced correctly and the order is saved.
     */
    @Test
    void testCreateOrder_successAndStockUpdated() {
        //Create and save a product with initial stock
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setSku("TEST-PRODUCT-000");
        product.setStock(10);
        ProductEntity savedProduct = productRepository.save(product);

        //Create an order request with a product item
        ProductItemRequest itemRequest = new ProductItemRequest();
        itemRequest.setProductId(savedProduct.getId().toString());
        itemRequest.setQuantity(5);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderItems(List.of(itemRequest));

        //Create the order
        OrderDto createdOrder = orderService.createOrder(orderRequest);

        //Verify the order is created successfully
        Assertions.assertNotNull(createdOrder);
        Assertions.assertEquals(1, createdOrder.getItems().size());
        Assertions.assertEquals(5, createdOrder.getItems().get(0).getQuantity());

        //Verify the product stock is updated
        ProductEntity updatedProduct = productRepository.findById(product.getId()).orElseThrow();
        Assertions.assertEquals(5, updatedProduct.getStock());  // Stock reduced from 10 to 5

        //Verify the order is saved in the database
        OrderEntity savedOrder = orderRepository.findById(UUID.fromString(createdOrder.getId())).orElseThrow();
        Assertions.assertEquals(1, savedOrder.getItems().size());
    }

    /**
     * Test case for handling insufficient stock during order creation.
     * Ensures that the transaction is rolled back and no changes are persisted.
     */
    @Test
        void testCreateOrder_insufficientStock_rollback() {

        //Create and save a product with limited stock
        ProductEntity product = new ProductEntity();
        product.setName("Test Product1");
        product.setStock(3);
        product.setSku("TEST-PRODUCT-001");
        productRepository.save(product);

        //Create an order request with a quantity exceeding stock
        ProductItemRequest itemRequest = new ProductItemRequest();
        itemRequest.setProductId(product.getId().toString());
        itemRequest.setQuantity(5);  // more than stock

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderItems(List.of(itemRequest));

        //Verify that an exception is thrown and the transaction is rolled back
        Assertions.assertThrows(InsufficientStockException.class, () -> {
            orderService.createOrder(orderRequest);
        });

        //Verify no orders are saved
        Assertions.assertEquals(Optional.empty(), orderRepository.findById(product.getId()));

        //Verify the product stock remains unchanged
        ProductEntity unchangedProduct = productRepository.findById(product.getId()).orElseThrow();
        Assertions.assertEquals(3, unchangedProduct.getStock());
    }
}
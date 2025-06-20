package com.cams.inventory.management.dto;

import com.cams.inventory.management.entity.constant.OrderStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object (DTO) for representing an order.
 * Contains the order ID, status, and a list of order items.
 */
@Data
public class OrderDto {

    /**
     * The unique identifier of the order.
     */
    private String id;

    /**
     * The current status of the order.
     */
    private OrderStatus status;

    /**
     * The list of items associated with the order.
     */
    private List<OrderItemDto> items = new ArrayList<>();
}

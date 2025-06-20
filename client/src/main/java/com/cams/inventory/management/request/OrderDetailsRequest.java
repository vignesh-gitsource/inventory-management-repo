package com.cams.inventory.management.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * Request object representing the details of an order.
 * Contains the order ID, status, and a list of item details.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsRequest {

    /**
     * The unique identifier for the order.
     */
    private String id;

    /**
     * The current status of the order.
     */
    private String status;

    /**
     * The list of items included in the order.
     */
    private List<OrderItemDetailsRequest> items;
}


package com.cams.inventory.management.request;

import lombok.Data;

import java.util.List;

/**
 * Request object representing an order.
 * Contains a list of product items in the order.
 */
@Data
public class OrderRequest {

    /**
     * The list of product items in the order.
     */
    List<ProductItemRequest> orderItems;
}

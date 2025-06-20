package com.cams.inventory.management.entity.constant;

/**
 * Enum representing the status of an order in the system.
 */
public enum OrderStatus {

    /**
     * Indicates that the order is pending and has not been processed yet.
     */
    PENDING,

    /**
     * Indicates that the order has been completed successfully.
     */
    COMPLETED,

    /**
     * Indicates that the order has been cancelled.
     */
    CANCELLED;
}

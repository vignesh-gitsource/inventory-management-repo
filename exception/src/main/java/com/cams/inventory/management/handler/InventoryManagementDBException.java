package com.cams.inventory.management.handler;

/**
 * Custom exception for handling database-related errors in the Inventory Management system.
 * Extends {@link RuntimeException} to allow unchecked exceptions.
 */
public class InventoryManagementDBException extends RuntimeException {

    /**
     * Constructs a new InventoryManagementDBException with the specified detail message.
     *
     * @param message the detail message explaining the exception
     */
    public InventoryManagementDBException(String message) {
        super(message);
    }

    /**
     * Constructs a new InventoryManagementDBException with the specified detail message and cause.
     *
     * @param message the detail message explaining the exception
     * @param cause   the cause of the exception
     */
    public InventoryManagementDBException(String message, Throwable cause) {
        super(message, cause);
    }
}

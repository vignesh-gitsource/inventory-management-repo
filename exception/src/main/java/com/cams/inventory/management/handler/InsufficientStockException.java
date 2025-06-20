package com.cams.inventory.management.handler;

/**
 * Exception thrown when there is insufficient stock for a requested operation.
 */
public class InsufficientStockException extends RuntimeException {

    /**
     * Constructs a new InsufficientStockException with the specified detail message.
     *
     * @param message the detail message
     */
    public InsufficientStockException(String message) {
        super(message);
    }

    /**
     * Constructs a new InsufficientStockException with the specified detail message
     * and cause.
     *
     * @param message the detail message
     * @param cause   the cause of the exception
     */
    public InsufficientStockException(String message, Throwable cause) {
        super(message, cause);
    }
}

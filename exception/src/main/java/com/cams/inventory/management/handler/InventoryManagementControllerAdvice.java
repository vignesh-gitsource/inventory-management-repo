package com.cams.inventory.management.handler;

import com.cams.inventory.management.response.ApiResponse;
import jakarta.persistence.OptimisticLockException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


/**
 * Global exception handler for the Inventory Management system.
 * Provides centralized exception handling for various types of exceptions.
 */
@ControllerAdvice
public class InventoryManagementControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Handles HTTP request method not supported exceptions.
     *
     * @param ex      the exception thrown when an unsupported HTTP method is used
     * @param headers the HTTP headers
     * @param status  the HTTP status code
     * @param request the web request
     * @return a ResponseEntity containing an ApiResponse with error details
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatusCode status,
                                                                         WebRequest request) {
        ApiResponse<String, List<Object>> apiResponse = ApiResponse.<String, List<Object>>builder()
                .success(false)
                .message("Method Not Allowed")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(status.value()));
    }

    /**
     * Handles validation exceptions for handler methods.
     *
     * @param ex      the exception thrown when validation fails
     * @param headers the HTTP headers
     * @param status  the HTTP status code
     * @param request the web request
     * @return a ResponseEntity containing an ApiResponse with validation error details
     */
    @Override
    protected ResponseEntity<Object> handleHandlerMethodValidationException(HandlerMethodValidationException ex,
                                                                            HttpHeaders headers,
                                                                            HttpStatusCode status,
                                                                            WebRequest request) {
        List<String> errors = ex.getAllValidationResults().stream()
                .flatMap(results -> results.getResolvableErrors().stream())
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .toList();

        ApiResponse<String, List<Object>> apiResponse = ApiResponse.<String, List<Object>>builder()
                .message("Validation Failed")
                .errors(errors).build();

        return new ResponseEntity<>(apiResponse, HttpStatusCode.valueOf(status.value()));
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex, HttpHeaders headers,
                                                                    HttpStatusCode status, WebRequest request) {
        ApiResponse<String, List<Object>> apiResponse = ApiResponse.<String, List<Object>>builder()
                .success(false)
                .message("Resource not found: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(status.value()));    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                          HttpHeaders headers, HttpStatusCode status,
                                                                          WebRequest request) {

        ApiResponse<String, List<Object>> apiResponse = ApiResponse.<String, List<Object>>builder()
                .success(false)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.valueOf(status.value()));
    }

    /**
     * Handles database-related exceptions in the Inventory Management system.
     *
     * @param ex the exception thrown when a database error occurs
     * @return a ResponseEntity containing an ApiResponse with error details
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InventoryManagementDBException.class)
    protected ResponseEntity<Object> inventoryManagementDBResponse(InventoryManagementDBException ex) {
        ApiResponse<String, List<Object>> apiResponse = ApiResponse.<String, List<Object>>builder()
                .success(false)
                .message("Database Error: " + ex.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> resourceNotFoundException(ResourceNotFoundException ex) {
        ApiResponse<String, List<Object>> apiResponse = ApiResponse.<String, List<Object>>builder()
                .success(false)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientStockException.class)
    protected ResponseEntity<Object> insufficientStockException(InsufficientStockException ex) {
        ApiResponse<String, List<Object>> apiResponse = ApiResponse.<String, List<Object>>builder()
                .success(false)
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles optimistic locking failure exceptions.
     * This exception occurs when a concurrent update to a resource causes a conflict.
     *
     * @param ex the exception thrown when an optimistic locking conflict occurs
     * @return a ResponseEntity containing an ApiResponse with error details and a conflict status
     */
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    protected ResponseEntity<Object> objectOptimisticLockingFailureException(ObjectOptimisticLockingFailureException ex) {
        ApiResponse<String, List<Object>> apiResponse = ApiResponse.<String, List<Object>>builder()
                .success(false)
                .message("Conflict occurred: The product was modified by another request. Please try again.")
                .build();
        return new ResponseEntity<>(apiResponse, HttpStatus.CONFLICT);
    }

    /**
     * This method handles the Exception in the service.
     *
     * @param ex      The exception.
     * @param request Request.
     * @return The response entity.
     */
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> exceptionHandler(Exception ex, WebRequest request) {
        ApiResponse<String, List<Object>> apiResponse = ApiResponse.<String, List<Object>>builder()
                .success(false)
                .errors(Collections.emptyList())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(apiResponse, INTERNAL_SERVER_ERROR);
    }
}
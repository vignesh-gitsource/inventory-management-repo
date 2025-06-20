package com.cams.inventory.management.product;

import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.request.OrderDetailsRequest;
import com.cams.inventory.management.request.ProductRequest;
import com.cams.inventory.management.response.ApiResponse;
import com.cams.inventory.management.service.product.ProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * REST controller for creating new products.
 * Handles incoming product creation requests and delegates processing to the ProductService layer.
 */
@RestController
@RequestMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    // Logger for logging events and debugging
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    // Product service to handle business logic
    private final ProductService productService;

    /**
     * Constructor-based injection of ProductService.
     *
     * @param productService the product service instance
     */
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Endpoint to create one or more products.
     *
     * @param productRequests the list of product requests to be created
     * @return ResponseEntity containing the API response with success data or validation errors
     */
    @PostMapping("/v1/create-products")
    public ResponseEntity<Object> createProduct(@RequestBody @Valid List<ProductRequest> productRequests) {
        log.info("Entered CreateProductApi with {} productRequests", productRequests.size());

        // Call service method to create products; returns a pair of valid ProductDtos and error messages
        Pair<List<ProductDto>, List<String>> result = productService.createProduct(productRequests);

        log.info("Product creation result: {} products created, {} errors found",
                result.getFirst().size(), result.getSecond().size());

        // Build API response using generic types: errors as List<String>, data as List<ProductDto>
        ApiResponse<String, List<ProductDto>> apiResponse = ApiResponse.<String, List<ProductDto>>builder()
                .success(!result.getFirst().isEmpty()) // success = true if any products were created
                .data(result.getFirst())               // set list of successfully created products
                .errors(result.getSecond())            // set validation or duplication errors
                .build();

        //Status determination based on whether any products were created
        HttpStatus status = result.getFirst().isEmpty() ? HttpStatus.OK : HttpStatus.CREATED;

        // Return the response entity with appropriate status and body
        return ResponseEntity.status(status).body(apiResponse);
    }

    /**
     * Endpoint to retrieve products with stock below a specified threshold.
     *
     * @param stockThreshold the stock level threshold to filter low-stock products
     * @return an ApiResponse containing a list of low-stock products and a success flag
     */
    public ApiResponse<String, List<ProductDto>> getLowStockProducts(@RequestParam int stockThreshold) {
        log.info("Fetching products with stock below threshold: {}", stockThreshold); // Log the stock threshold being used

        // Retrieve the list of products with stock below the specified threshold
        List<ProductDto> lowStockProducts = productService.getLowStockProducts(stockThreshold);

        // Check if any low-stock products were found
        boolean hasLowStockProductsFound = !lowStockProducts.isEmpty();

        // Build and return the API response
        return ApiResponse.<String, List<ProductDto>>builder()
                .success(hasLowStockProductsFound) // Set success to true if products were found
                .data(lowStockProducts) // Include the list of low-stock products in the response
                .errors(hasLowStockProductsFound ? null : Collections.singletonList("No products found with the given stock threshold")) // Add an error message if no products were found
                .build();
    }


    /**
     * Endpoint to retrieve a summary of product details based on the provided order details.
     *
     * @param orderDetailsRequests the list of order details requests to calculate the product summary
     * @return an ApiResponse containing the success status, product summary data, or error messages
     */
    @PostMapping("/v1/product-summary")
    public ApiResponse<String, Map<String, BigDecimal>> getProductSummaryDetails(@RequestBody List<OrderDetailsRequest> orderDetailsRequests) {

        log.info("Retrieving product summary details for {} order requests", orderDetailsRequests.size());
        // Call the service layer to retrieve the product summary details
        Map<String, BigDecimal> productSummary = productService.getProductSummaryDetails(orderDetailsRequests);

        log.info("Product summary retrieval completed. Summary size: {}", productSummary.size());
        // Check if the product summary retrieval was successful
        boolean hasSummaryRetrieved = !productSummary.isEmpty();

        // Build and return the API response with success status, data, and errors
        return ApiResponse.<String, Map<String, BigDecimal>>builder()
                .success(hasSummaryRetrieved) // Indicate success or failure
                .data(hasSummaryRetrieved ? productSummary : null) // Include product summary if successful
                .errors(hasSummaryRetrieved ? null : Collections.singletonList("No product summary found for the given orders")) // Include error message if failed
                .build();
    }
}

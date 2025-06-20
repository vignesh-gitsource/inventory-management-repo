package com.cams.inventory.management.service.product;

import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.request.OrderDetailsRequest;
import com.cams.inventory.management.request.ProductRequest;
import org.springframework.data.util.Pair;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * Service interface for handling product-related operations.
 */
public interface ProductService {

    /**
     * Creates a product based on the provided product requests.
     *
     * @param productRequest List of product requests to create products from.
     * @return A pair containing a list of created ProductDto objects and a list of error messages (if any).
     */
    Pair<List<ProductDto>, List<String>> createProduct(List<ProductRequest> productRequest);

    /**
     * Retrieves a list of products that have stock below the specified threshold.
     *
     * @param stockThreshold the stock level threshold to filter low-stock products
     * @return a list of ProductDto objects representing products with low stock
     */
    List<ProductDto> getLowStockProducts(int stockThreshold);

    /**
     * Retrieves a summary of product details based on the provided order details requests.
     *
     * @param orderDetailsRequests List of order details requests to calculate the product summary.
     * @return A map where the key is the product identifier and the value is the total amount for that product.
     */
    Map<String, BigDecimal> getProductSummaryDetails(List<OrderDetailsRequest> orderDetailsRequests);
}

package com.cams.inventory.management.service.product.impl;

import com.cams.inventory.management.dao.product.ProductDao;
import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.mapper.ProductMapper;
import com.cams.inventory.management.request.OrderDetailsRequest;
import com.cams.inventory.management.request.ProductRequest;
import com.cams.inventory.management.service.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service implementation for handling product-related operations.
 * Provides business logic for creating and managing products.
 */
@Service
public class ProductServiceImpl implements ProductService {

    /**
     * Logger instance for logging events in the ProductServiceImpl class.
     */
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    /**
     * Data Access Object (DAO) for product operations.
     */
    private final ProductDao productDao;

    /**
     * Mapper for transforming product data between different layers.
     */
    private final ProductMapper productMapper;

    /**
     * Constructor for `ProductServiceImpl`.
     *
     * @param productDao    the DAO layer for product operations
     * @param productMapper the mapper for transforming product data
     */
    public ProductServiceImpl(ProductDao productDao,
                              ProductMapper productMapper) {
        this.productDao = productDao;
        this.productMapper = productMapper;
    }


    /**
     * Creates a product based on the provided product requests.
     *
     * @param productRequestList List of product requests to create products from.
     * @return A pair containing a list of created ProductDto objects and a list of error messages (if any).
     */
    @Override
    public Pair<List<ProductDto>, List<String>> createProduct(List<ProductRequest> productRequestList) {

        logger.debug("Creating products from the provided product request list: {}", productRequestList);
        // Extract all SKUs from incoming list
        List<String> incomingSkus = productRequestList.stream()
                .map(ProductRequest::getSku)
                .filter(StringUtils::hasText)
                .toList();

        logger.info("Extracted SKUs from incoming product requests: {}", incomingSkus);
        // Get all existing products from DB based on incoming SKUs
        List<ProductDto> existingProducts = productDao.getProductsBySku(incomingSkus);

        logger.info("Fetched existing products list size : {}", existingProducts.size());
        // Collect SKUs that already exist in DB
        Set<String> existingSkus = existingProducts.stream()
                .map(ProductDto::getSku)
                .collect(Collectors.toSet());

        logger.info("Existing SKUs in the database: {}", existingSkus);
        List<String> errorsList = new ArrayList<>();
        // Separate out valid requests (not in DB) and collect errors for duplicates
        List<ProductRequest> validRequests = productRequestList.stream()
                .filter(productRequest -> {
                    String sku = productRequest.getSku();
                    if (!StringUtils.hasText(sku) || existingSkus.contains(sku)) {
                        errorsList.add("Product with SKU " + sku + " already exists.");
                        return false;
                    }
                    return true;
                })
                .toList();

        List<ProductDto> productDtos = productMapper.transformProductRequestsListToProductDtoList(validRequests);


        // Insert into DB
        if (!productDtos.isEmpty()) {
            productDtos = productDao.createProduct(productDtos);
        }

        //Return both the inserted products and erred items.
        return Pair.of(productDtos, errorsList);
    }

    /**
     * Retrieves a list of products that have stock below a specified threshold.
     *
     * @param stockThreshold The stock level below which products are considered low stock.
     * @return A list of ProductDto objects representing products with stock below the threshold.
     */
    @Override
    public List<ProductDto> getLowStockProducts(int stockThreshold) {

        logger.debug("Fetching products with stock below the threshold: {}", stockThreshold);
        // Fetch all products with stock below the threshold
        List<ProductDto> productsList = productDao.getAllProducts();

        logger.info("Total products fetched from the database: {}", productsList.size());

        // Filter products that are below the stock threshold
        return productsList.stream()
                .filter(p -> p.getStock() < stockThreshold)
                .toList();
    }

    /**
     * Retrieves a summary of product details based on the provided order details requests.
     *
     * @param orderDetailsRequests List of order details requests to calculate the product summary.
     * @return A map where the key is the product identifier and the value is the total amount for that product.
     */
    @Override
    public Map<String, BigDecimal> getProductSummaryDetails(List<OrderDetailsRequest> orderDetailsRequests) {

        logger.debug("Calculating product summary details from order details requests: {}", orderDetailsRequests);

        // Filter out order details requests that do not have items and then group by product name
        return orderDetailsRequests.stream()
                .filter(orderDetailsRequest -> orderDetailsRequest.getItems() != null)
                .flatMap(orderDetailsRequest -> orderDetailsRequest.getItems().stream())
                .collect(Collectors.groupingBy(item -> item.getProduct().getName(),
                        Collectors.reducing(BigDecimal.ZERO, item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())),
                                BigDecimal::add)));
    }
}

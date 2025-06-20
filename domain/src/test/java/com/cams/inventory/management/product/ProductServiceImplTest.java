package com.cams.inventory.management.product;

import com.cams.inventory.management.dao.product.ProductDao;
import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.entity.constant.OrderStatus;
import com.cams.inventory.management.mapper.ProductMapper;
import com.cams.inventory.management.request.OrderDetailsRequest;
import com.cams.inventory.management.request.OrderItemDetailsRequest;
import com.cams.inventory.management.request.ProductDetailsRequest;
import com.cams.inventory.management.request.ProductRequest;
import com.cams.inventory.management.service.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.util.Pair;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.util.*;

/** ProductServiceImplTest.java
 * This class contains unit tests for the ProductServiceImpl class.
 * It tests methods for retrieving low stock products, summarizing product details,
 * and creating new products.
 */
@ExtendWith(SpringExtension.class)
class ProductServiceImplTest {

    /**
     * Injects the ProductServiceImpl instance and mocks the dependencies.
     */
    @InjectMocks
    private ProductServiceImpl productServiceImpl;

    /**
     * Mocks the ProductDao and ProductMapper dependencies.
     */
    @Mock
    ProductDao productDao;

    /**
     * Mocks the ProductMapper dependency.
     */
    @Mock
    ProductMapper productMapper;

    /**
     * Tests the retrieval of low stock products.
     * It checks if the method correctly identifies products with stock below a specified threshold.
     */
    @Test
    void testGetLowStockProducts(){

        List<ProductDto> products = List.of(
                new ProductDto(String.valueOf(UUID.randomUUID()), "Apple", "173546", BigDecimal.valueOf(10), 50),
                new ProductDto(String.valueOf(UUID.randomUUID()),"Banana", "982651", BigDecimal.valueOf(5), 120),
                new ProductDto(String.valueOf(UUID.randomUUID()),"Cherry", "362519", BigDecimal.valueOf(20), 100)
        );

        Mockito.when(productDao.getAllProducts()).thenReturn(products);

        List<ProductDto> results = productServiceImpl.getLowStockProducts(60);
        Assertions.assertFalse(results.isEmpty());
        Assertions.assertEquals(1, results.size());
        Assertions.assertEquals("Apple", results.get(0).getName());
    }

    /**
     * Tests the product summary details.
     * It checks if the method correctly summarizes the total quantity of products ordered.
     */
    @Test
    void testProductSummaryDetails(){

        ProductDetailsRequest apple = new ProductDetailsRequest(String.valueOf(UUID.randomUUID()), "Apple", "173546", BigDecimal.valueOf(10), 50);
        ProductDetailsRequest banana = new ProductDetailsRequest(String.valueOf(UUID.randomUUID()),"Banana", "982651", BigDecimal.valueOf(5), 120);

        OrderItemDetailsRequest orderItemDetailsRequest = new OrderItemDetailsRequest(String.valueOf(UUID.randomUUID()),2, apple);
        OrderItemDetailsRequest orderItemDetailsRequest1 = new OrderItemDetailsRequest(String.valueOf(UUID.randomUUID()),3, banana);
        OrderItemDetailsRequest orderItemDetailsRequest2 = new OrderItemDetailsRequest(String.valueOf(UUID.randomUUID()),1, apple);

        OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest(String.valueOf(UUID.randomUUID()), OrderStatus.COMPLETED.name(), List.of(orderItemDetailsRequest, orderItemDetailsRequest1));
        OrderDetailsRequest orderDetailsRequest1 = new OrderDetailsRequest(String.valueOf(UUID.randomUUID()), OrderStatus.COMPLETED.name(), List.of(orderItemDetailsRequest2));
        OrderDetailsRequest orderDetailsRequest2 = new OrderDetailsRequest(String.valueOf(UUID.randomUUID()), OrderStatus.COMPLETED.name(), null);

        List<OrderDetailsRequest> orders = List.of(orderDetailsRequest, orderDetailsRequest1, orderDetailsRequest2);

        Map<String, BigDecimal> productSummaryResults = productServiceImpl.getProductSummaryDetails(orders);
        Assertions.assertEquals(2, productSummaryResults.size());
        Assertions.assertEquals(BigDecimal.valueOf(30), productSummaryResults.get("Apple"));
        Assertions.assertEquals(BigDecimal.valueOf(15), productSummaryResults.get("Banana"));

    }

    /**
     * Tests the creation of new products.
     * It checks if the method correctly creates products and handles existing SKUs.
     */
    @Test
    void testCreateProduct_success(){

       List<ProductRequest> productRequestList =  List.of(new ProductRequest("Apple", "173546", BigDecimal.valueOf(10), 50),
                new ProductRequest("Banana", "982651", BigDecimal.valueOf(5), 120));

       List<ProductDto>  productDtoList = List.of(new ProductDto(String.valueOf(UUID.randomUUID()),"Apple", "173546", BigDecimal.valueOf(10), 50),
               new ProductDto(String.valueOf(UUID.randomUUID()),"Banana", "982651", BigDecimal.valueOf(5), 120));

       Mockito.when(productDao.getProductsBySku(Mockito.any())).thenReturn(Collections.emptyList());
       Mockito.when(productDao.createProduct(Mockito.any())).thenReturn(productDtoList);
       Mockito.when(productMapper.transformProductRequestsListToProductDtoList(productRequestList)).thenReturn(productDtoList);
      Pair<List<ProductDto>, List<String>> results = productServiceImpl.createProduct(productRequestList);

      Assertions.assertNotNull(results);
      Assertions.assertTrue(ObjectUtils.isEmpty(results.getSecond()));
      Assertions.assertFalse(ObjectUtils.isEmpty(results.getFirst()));
      Assertions.assertEquals(2, results.getFirst().size());

      Mockito.verify(productDao, Mockito.times(1)).createProduct(Mockito.any());
    }

    /**
     * Tests the creation of products with an existing SKU.
     * It checks if the method correctly identifies existing SKUs and returns appropriate results.
     */
    @Test
    void testCreateProduct_withExistingSku(){

        List<ProductRequest> productRequestList =  List.of(new ProductRequest("Apple", "173546", BigDecimal.valueOf(10), 50),
                new ProductRequest("Banana", "982651", BigDecimal.valueOf(5), 120));

        Mockito.when(productDao.createProduct(Mockito.any())).thenReturn(List.of(new ProductDto(String.valueOf(UUID.randomUUID()),"Apple", "173546", BigDecimal.valueOf(10), 50)));
        Mockito.when(productMapper.transformProductRequestsListToProductDtoList(productRequestList)).thenReturn(List.of(new ProductDto(String.valueOf(UUID.randomUUID()),"Banana", "982651", BigDecimal.valueOf(5), 120)));
        Pair<List<ProductDto>, List<String>> results = productServiceImpl.createProduct(productRequestList);

        Assertions.assertNotNull(results);
        Assertions.assertTrue(ObjectUtils.isEmpty(results.getSecond()));
        Assertions.assertFalse(ObjectUtils.isEmpty(results.getFirst()));
        Assertions.assertEquals(1, results.getFirst().size());
        Assertions.assertEquals("173546",results.getFirst().get(0).getSku());

        Mockito.verify(productDao, Mockito.times(1)).createProduct(Mockito.any());
    }

    /**
     * Tests the creation of products when the SKU already exists.
     * It checks if the method correctly identifies existing SKUs and returns appropriate results.
     */
    @Test
    void testCreateProduct_failure(){

        List<ProductRequest> productRequestList =  List.of(new ProductRequest("Apple", "173546", BigDecimal.valueOf(10), 50),
                new ProductRequest("Banana", "982651", BigDecimal.valueOf(5), 120));

        List<ProductDto>  productDtoList = List.of(new ProductDto(String.valueOf(UUID.randomUUID()),"Apple", "173546", BigDecimal.valueOf(10), 50),
                new ProductDto(String.valueOf(UUID.randomUUID()),"Banana", "982651", BigDecimal.valueOf(5), 120));

        Mockito.when(productDao.getProductsBySku(Mockito.any())).thenReturn(productDtoList);
        Mockito.when(productDao.createProduct(Mockito.any())).thenReturn(productDtoList);
        Mockito.when(productMapper.transformProductRequestsListToProductDtoList(Mockito.any())).thenReturn(Collections.emptyList());
        Pair<List<ProductDto>, List<String>> results = productServiceImpl.createProduct(productRequestList);

        Assertions.assertNotNull(results);
        Assertions.assertFalse(ObjectUtils.isEmpty(results.getSecond()));
        Assertions.assertTrue(ObjectUtils.isEmpty(results.getFirst()));
        Assertions.assertEquals(0, results.getFirst().size());

        Mockito.verify(productDao, Mockito.times(0)).createProduct(Mockito.any());
    }
}

package com.cams.inventory.management.product;

import com.cams.inventory.management.dao.product.ProductDao;
import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.mapper.ProductMapper;
import com.cams.inventory.management.request.ProductRequest;
import com.cams.inventory.management.service.product.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Retrieve products with stock below the specified threshold")
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
     * Tests the creation of new products.
     * It checks if the method correctly creates products and handles existing SKUs.
     */
    @Test
    @DisplayName("Create products test - with no existing SKUs")
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
    @DisplayName("Create products test - with one existing SKUs")
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
    @DisplayName("Create products test - with all SKUs present in db")
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

package com.cams.inventory.management.mapper;

import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.entity.product.ProductEntity;
import com.cams.inventory.management.request.ProductRequest;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper interface for transforming product data between different layers.
 * Utilizes MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring", implementationName = "ProductMapperImplV2")
public interface ProductMapper {

    /**
     * Transforms a list of ProductRequest objects into a list of ProductDto objects.
     *
     * @param productRequest the list of ProductRequest objects to transform
     * @return a list of ProductDto objects
     */
    List<ProductDto> transformProductRequestsListToProductDtoList(final List<ProductRequest> productRequest);

    /**
     * Transforms a list of ProductEntity objects into a list of ProductDto objects.
     *
     * @param productEntities the list of ProductEntity objects to transform
     * @return a list of ProductDto objects
     */
    List<ProductDto> transformProductEntityListToProductDtoList(final List<ProductEntity> productEntities);

    /**
     * Transforms a list of ProductDto objects into a list of ProductEntity objects.
     *
     * @param productDtoList the list of ProductDto objects to transform
     * @return a list of ProductEntity objects
     */
    List<ProductEntity> transformProductDtoListToProductEntityList(final List<ProductDto> productDtoList);

    /**
     * Transforms a single ProductEntity object into a ProductDto object.
     *
     * @param productEntity the ProductEntity object to transform
     * @return a ProductDto object
     */
    ProductDto transformProductEntityToProductDto(final ProductEntity productEntity);

    /**
     * Transforms a single ProductDto object into a ProductEntity object.
     *
     * @param productDto the ProductDto object to transform
     * @return a ProductEntity object
     */
    ProductEntity transformProductDtoToProductEntity(final ProductDto productDto);

}

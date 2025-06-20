package com.cams.inventory.management.mapper;

import com.cams.inventory.management.dto.OrderDto;
import com.cams.inventory.management.entity.order.OrderEntity;
import org.mapstruct.Mapper;

/**
 * Mapper interface for transforming between OrderEntity and OrderDto.
 * Utilizes MapStruct for automatic implementation generation.
 */
@Mapper(componentModel = "spring", implementationName = "orderMapperImpl")
public interface OrderMapper {

    /**
     * Transforms an OrderEntity object into an OrderDto object.
     *
     * @param orderEntity the OrderEntity to transform
     * @return the resulting OrderDto
     */
    OrderDto transformOrderEntityToOrderDto(OrderEntity orderEntity);
}

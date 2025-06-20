package com.cams.inventory.management.mapper;

import com.cams.inventory.management.dto.OrderDto;
import com.cams.inventory.management.dto.OrderItemDto;
import com.cams.inventory.management.dto.ProductDto;
import com.cams.inventory.management.entity.order.OrderEntity;
import com.cams.inventory.management.entity.order.OrderItemEntity;
import com.cams.inventory.management.entity.product.ProductEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-20T07:32:57+0530",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
)
@Component
public class orderMapperImpl implements OrderMapper {

    @Override
    public OrderDto transformOrderEntityToOrderDto(OrderEntity orderEntity) {
        if ( orderEntity == null ) {
            return null;
        }

        OrderDto orderDto = new OrderDto();

        if ( orderEntity.getId() != null ) {
            orderDto.setId( orderEntity.getId().toString() );
        }
        orderDto.setStatus( orderEntity.getStatus() );
        orderDto.setItems( orderItemEntityListToOrderItemDtoList( orderEntity.getItems() ) );

        return orderDto;
    }

    protected ProductDto productEntityToProductDto(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        ProductDto productDto = new ProductDto();

        if ( productEntity.getId() != null ) {
            productDto.setId( productEntity.getId().toString() );
        }
        productDto.setName( productEntity.getName() );
        productDto.setSku( productEntity.getSku() );
        productDto.setPrice( productEntity.getPrice() );
        productDto.setStock( productEntity.getStock() );

        return productDto;
    }

    protected OrderItemDto orderItemEntityToOrderItemDto(OrderItemEntity orderItemEntity) {
        if ( orderItemEntity == null ) {
            return null;
        }

        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.setQuantity( orderItemEntity.getQuantity() );
        orderItemDto.setProduct( productEntityToProductDto( orderItemEntity.getProduct() ) );

        return orderItemDto;
    }

    protected List<OrderItemDto> orderItemEntityListToOrderItemDtoList(List<OrderItemEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderItemDto> list1 = new ArrayList<OrderItemDto>( list.size() );
        for ( OrderItemEntity orderItemEntity : list ) {
            list1.add( orderItemEntityToOrderItemDto( orderItemEntity ) );
        }

        return list1;
    }
}

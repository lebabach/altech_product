package com.example.order.mapper;

import com.example.order.dto.OrderDTO;
import com.example.order.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author bachlb
 */
@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO orderToOrderDTO(Order order);

    Order orderDTOToOrder(OrderDTO orderDTO);

    void updateOrderFromDTO(OrderDTO orderDTO, @MappingTarget Order order);
}
package com.carrefour.eshop.mapper;

import com.carrefour.eshop.dto.CartDto;
import com.carrefour.eshop.dto.OrderDto;
import com.carrefour.eshop.entity.Cart;
import com.carrefour.eshop.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

  @Mapping(source = "entries", target = "entries")
  OrderDto toDto(Order order);
}

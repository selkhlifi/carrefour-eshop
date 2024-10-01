package com.carrefour.eshop.mapper;

import com.carrefour.eshop.entity.Cart;
import com.carrefour.eshop.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartToOrderMapper {
  Order toOrder(Cart cart);
}

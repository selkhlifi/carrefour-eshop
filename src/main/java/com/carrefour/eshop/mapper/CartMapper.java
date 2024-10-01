package com.carrefour.eshop.mapper;

import com.carrefour.eshop.dto.CartDto;
import com.carrefour.eshop.entity.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CartEntryMapper.class})
public interface CartMapper {

  @Mapping(source = "entries", target = "entries")
  CartDto toDto(Cart cart);
}

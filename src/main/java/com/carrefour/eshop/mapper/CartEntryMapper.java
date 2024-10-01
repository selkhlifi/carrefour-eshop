package com.carrefour.eshop.mapper;


import com.carrefour.eshop.dto.AddCartEntryDto;
import com.carrefour.eshop.dto.CartEntryDto;
import com.carrefour.eshop.entity.CartEntry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartEntryMapper {
  @Mapping(source = "product.code", target = "productCode")
  CartEntryDto toDto(CartEntry entity);
  CartEntry toModel(AddCartEntryDto dto);
}

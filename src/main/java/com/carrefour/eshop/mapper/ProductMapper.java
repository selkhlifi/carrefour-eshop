package com.carrefour.eshop.mapper;

import com.carrefour.eshop.dto.ProductDto;
import com.carrefour.eshop.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  // Define mapping rules between source and destination fields
  ProductDto toDto(Product product);
}


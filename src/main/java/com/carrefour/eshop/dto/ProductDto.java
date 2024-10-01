package com.carrefour.eshop.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
  String code;
  String name;
  String description;
  BigDecimal price;
}

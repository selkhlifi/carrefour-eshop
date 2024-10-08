package com.carrefour.eshop.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {
  List<CartEntryDto> entries;
  BigDecimal total;
}

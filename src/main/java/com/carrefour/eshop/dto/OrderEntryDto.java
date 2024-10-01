package com.carrefour.eshop.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderEntryDto {
  String productCode;
  int quantity;
  BigDecimal price;
}


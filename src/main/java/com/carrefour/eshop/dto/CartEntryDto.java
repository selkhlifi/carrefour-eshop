package com.carrefour.eshop.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CartEntryDto {
  String productCode;
  int quantity;
  BigDecimal price;
}

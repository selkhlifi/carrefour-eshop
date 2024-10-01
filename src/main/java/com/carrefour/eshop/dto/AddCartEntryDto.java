package com.carrefour.eshop.dto;

import lombok.Data;

@Data
public class AddCartEntryDto {
  String productCode;
  int quantity;
}

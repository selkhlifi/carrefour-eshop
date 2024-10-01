package com.carrefour.eshop.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderDto {
  String orderCode;
  List<OrderEntryDto> entries;
  BigDecimal total;
  String status;
}

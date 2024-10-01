package com.carrefour.eshop.entity;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("order")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  @Id
  private String id;
  private String orderCode;
  private Customer customer;
  private BigDecimal total;
  private List<CartEntry> entries;
  private OrderStatus status = OrderStatus.CREATED;
}

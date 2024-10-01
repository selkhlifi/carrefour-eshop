package com.carrefour.eshop.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("cart-entry")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartEntry {
  @Id
  private String id;

  private Cart cart;

  private Product product;
  private int quantity;
  private BigDecimal price;
}

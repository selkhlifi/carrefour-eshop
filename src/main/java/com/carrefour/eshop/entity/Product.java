package com.carrefour.eshop.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  @Id
  private String id;
  private String code;
  private String name;
  private String description;
  private BigDecimal price;
}

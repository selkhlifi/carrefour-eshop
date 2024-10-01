package com.carrefour.eshop.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("customer")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  @Id
  private String id;
  String firstName;
  String lastName;
  String username;
  String password;
  Cart cart;
}

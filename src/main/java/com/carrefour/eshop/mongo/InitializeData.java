package com.carrefour.eshop.mongo;

import com.carrefour.eshop.entity.Customer;
import com.carrefour.eshop.entity.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class InitializeData {

  private final MongoTemplate mongoTemplate;

  public InitializeData(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @PostConstruct
  public void init() throws IOException {
    System.out.println("Initializing data");

    ObjectMapper objectMapper = new ObjectMapper();

    // users.json file should be present inside resources > data folder
    List<Customer> customers =
        objectMapper.readValue(
            new ClassPathResource("/db/changelog/customers.json").getFile(),
            new TypeReference<>() {});

    List<Product> products =
        objectMapper.readValue(
            new ClassPathResource("/db/changelog/products.json").getFile(),
            new TypeReference<>() {});

    // Insert the data into the database
    mongoTemplate.insertAll(customers);
    mongoTemplate.insertAll(products);

    System.out.println("Data initialization completed successfully");
  }
}


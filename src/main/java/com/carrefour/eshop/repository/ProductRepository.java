package com.carrefour.eshop.repository;

import com.carrefour.eshop.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
  Mono<Product> findByCode(String code);
}


package com.carrefour.eshop.repository;

import com.carrefour.eshop.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {
  Mono<Customer> findByUsername(String username);
}

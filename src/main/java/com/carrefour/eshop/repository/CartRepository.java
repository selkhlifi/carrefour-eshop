package com.carrefour.eshop.repository;


import com.carrefour.eshop.entity.Cart;
import com.carrefour.eshop.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface CartRepository extends ReactiveCrudRepository<Cart, String> {

  Mono<Cart> findByCustomer(Customer customer);

}

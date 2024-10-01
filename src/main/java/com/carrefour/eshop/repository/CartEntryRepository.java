package com.carrefour.eshop.repository;

import com.carrefour.eshop.entity.Cart;
import com.carrefour.eshop.entity.CartEntry;
import com.carrefour.eshop.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CartEntryRepository extends ReactiveMongoRepository<CartEntry, String> {

  Flux<CartEntry> findAllByCart(Cart cat);
  Mono<Void> deleteByProductCode(String productCode);
  Mono<CartEntry> findByCartAndProduct(Cart currentCart, Product product);
  Flux<CartEntry> findByCart(Cart cart);
}

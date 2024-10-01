package com.carrefour.eshop.repository;


import com.carrefour.eshop.entity.Order;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<Order, String> {

}

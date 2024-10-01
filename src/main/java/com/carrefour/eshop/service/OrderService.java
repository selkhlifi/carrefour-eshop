package com.carrefour.eshop.service;

import com.carrefour.eshop.entity.Cart;
import com.carrefour.eshop.entity.Order;
import com.carrefour.eshop.entity.OrderStatus;
import com.carrefour.eshop.mapper.CartToOrderMapper;
import com.carrefour.eshop.repository.OrderRepository;
import java.util.UUID;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class OrderService {

  private final CartService cartService;
  private final CartToOrderMapper cartToOrderMapper;
  private final OrderRepository orderRepository;
  private final OrderEventProducer orderEventProducer;

  public OrderService(CartService cartService, CartToOrderMapper cartToOrderMapper,
      OrderRepository orderRepository, OrderEventProducer orderEventProducer) {
    this.cartService = cartService;
    this.cartToOrderMapper = cartToOrderMapper;
    this.orderRepository = orderRepository;
    this.orderEventProducer = orderEventProducer;
  }

  public Mono<Order> placeOrder() {
    return cartService.getCurrentCart()
        .map(this::createOrderFromCart)
        .flatMap(orderRepository::save)
        .doOnNext(order -> orderEventProducer.sendOrderCreatedEvent(order.getOrderCode()));
  }


  private Order createOrderFromCart(Cart cart) {
    var order = cartToOrderMapper.toOrder(cart);
    order.setOrderCode(UUID.randomUUID().toString());
    order.setStatus(OrderStatus.CREATED);
    return order;
  }

}

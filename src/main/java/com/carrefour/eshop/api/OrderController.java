package com.carrefour.eshop.api;

import com.carrefour.eshop.dto.OrderDto;
import com.carrefour.eshop.mapper.OrderMapper;
import com.carrefour.eshop.service.OrderService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final OrderService orderService;
  private final OrderMapper orderMapper;

  public OrderController(OrderService orderService, OrderMapper orderMapper) {
    this.orderService = orderService;
    this.orderMapper = orderMapper;
  }

  @PostMapping
  public Mono<OrderDto> placeOrder() {
    return orderService.placeOrder().map(orderMapper::toDto);
  }

  @GetMapping("/{orderCode}")
  public Mono<OrderDto> getOrder() {
    return Mono.just(OrderDto.builder().build());
  }

  @GetMapping("/")
  public Flux<OrderDto> getAllOrders() {
    return Flux.fromIterable(List.of(OrderDto.builder().build()));
  }
}

package com.carrefour.eshop.api;

import com.carrefour.eshop.dto.OrderDto;
import com.carrefour.eshop.mapper.OrderMapper;
import com.carrefour.eshop.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

  private final OrderService orderService;
  private final OrderMapper orderMapper;

  public OrderController(OrderService orderService, OrderMapper orderMapper) {
    this.orderService = orderService;
    this.orderMapper = orderMapper;
  }

  @Operation(summary = "Place a new order", description = "Creates a new order and returns the details of the placed order.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Order placed successfully"),
      @ApiResponse(responseCode = "400", description = "Bad request, invalid input data"),
      @ApiResponse(responseCode = "500", description = "Internal server error, order could not be placed")
  })
  @PostMapping
  public Mono<OrderDto> placeOrder() {
    return orderService.placeOrder()
        .map(orderMapper::toDto);
  }
}


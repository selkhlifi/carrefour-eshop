package com.carrefour.eshop.api;

import com.carrefour.eshop.dto.AddCartEntryDto;
import com.carrefour.eshop.dto.CartDto;
import com.carrefour.eshop.dto.CartEntryDto;
import com.carrefour.eshop.mapper.CartEntryMapper;
import com.carrefour.eshop.mapper.CartMapper;
import com.carrefour.eshop.service.CartService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/carts/current")
public class CartController {

  private final CartService cartService;
  private final CartMapper cartMapper;
  private final CartEntryMapper cartEntryMapper;

  public CartController(CartService cartService, CartMapper cartMapper,
      CartEntryMapper cartEntryMapper) {
    this.cartService = cartService;
    this.cartMapper = cartMapper;
    this.cartEntryMapper = cartEntryMapper;
  }

  @GetMapping
  public Mono<CartDto> getCart() {
    return cartService
        .getCurrentCart()
        .map(cartMapper::toDto);
  }

  @PostMapping("/entry")
  public Mono<CartEntryDto> addEntry(@RequestBody AddCartEntryDto addCartEntryDto) {
    return cartService.addOrUpdateEntry(addCartEntryDto).map(cartEntryMapper::toDto);
  }

  @DeleteMapping("/entry/{productCode}")
  public Mono<CartDto> removeEntry(@PathVariable String productCode) {
    return cartService.removeEntry(productCode).map(cartMapper::toDto);
  }
}

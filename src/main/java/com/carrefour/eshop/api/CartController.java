package com.carrefour.eshop.api;

import com.carrefour.eshop.dto.AddCartEntryDto;
import com.carrefour.eshop.dto.CartDto;
import com.carrefour.eshop.dto.CartEntryDto;
import com.carrefour.eshop.mapper.CartEntryMapper;
import com.carrefour.eshop.mapper.CartMapper;
import com.carrefour.eshop.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/carts/current")
public class CartController {

  private final CartService cartService;
  private final CartMapper cartMapper;
  private final CartEntryMapper cartEntryMapper;

  public CartController(CartService cartService, CartMapper cartMapper, CartEntryMapper cartEntryMapper) {
    this.cartService = cartService;
    this.cartMapper = cartMapper;
    this.cartEntryMapper = cartEntryMapper;
  }

  @Operation(summary = "Get the current shopping cart", description = "Fetches the current shopping cart for the logged-in user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the cart"),
      @ApiResponse(responseCode = "404", description = "Cart not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @GetMapping
  public Mono<CartDto> getCart() {
    return cartService.getCurrentCart().map(cartMapper::toDto);
  }

  @Operation(summary = "Add a product to the cart", description = "Adds a new product entry to the current cart or updates the quantity if it already exists.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product entry successfully added or updated"),
      @ApiResponse(responseCode = "400", description = "Invalid product details"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @PostMapping("/entry")
  public Mono<CartEntryDto> addEntry(@RequestBody AddCartEntryDto addCartEntryDto) {
    return cartService.addOrUpdateEntry(addCartEntryDto).map(cartEntryMapper::toDto);
  }

  @Operation(summary = "Remove a product from the cart", description = "Removes the specified product entry from the current cart.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product entry successfully removed"),
      @ApiResponse(responseCode = "404", description = "Product entry not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
  @DeleteMapping("/entry/{productCode}")
  public Mono<CartDto> removeEntry(@PathVariable String productCode) {
    return cartService.removeEntry(productCode).map(cartMapper::toDto);
  }
}


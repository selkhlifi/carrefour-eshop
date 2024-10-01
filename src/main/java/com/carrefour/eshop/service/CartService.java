package com.carrefour.eshop.service;

import com.carrefour.eshop.dto.AddCartEntryDto;
import com.carrefour.eshop.entity.Cart;
import com.carrefour.eshop.entity.CartEntry;
import com.carrefour.eshop.entity.Customer;
import com.carrefour.eshop.entity.Product;
import com.carrefour.eshop.repository.CartEntryRepository;
import com.carrefour.eshop.repository.CartRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartService {
  private final CartRepository cartRepository;
  private final CartEntryRepository cartEntryRepository;
  private final CustomerService customerService;
  private final ProductService productService;

  public CartService(CartRepository cartRepository, CartEntryRepository cartEntryRepository,
      CustomerService customerService, ProductService productService) {
    this.cartRepository = cartRepository;
    this.cartEntryRepository = cartEntryRepository;
    this.customerService = customerService;
    this.productService = productService;
  }

  public Mono<Cart> getCurrentCart() {
    return customerService
        .getCurrentCustomer()
        .flatMap(customer -> cartRepository.findByCustomer(customer)
            .switchIfEmpty(createNewCart(customer)));
  }

  private Mono<Cart> createNewCart(Customer customer) {
    var newCart = Cart.builder()
        .customer(customer)
        .entries(Collections.emptyList())
        .total(BigDecimal.ZERO)
        .build();

    return cartRepository.save(newCart);
  }

  public Flux<CartEntry> getCartEntries(Cart cart) {
    return cartEntryRepository.findAllByCart(cart);
  }

  public Mono<CartEntry> addOrUpdateEntry(AddCartEntryDto addCartEntryDto) {
    return Mono.zip(getCurrentCart(), productService.findProductByCode(addCartEntryDto.getProductCode()))
        .flatMap(tuple -> {
          Cart currentCart = tuple.getT1();
          Product product = tuple.getT2();

          return findExistingCartEntry(currentCart, product.getCode())
              .map(existingEntry -> updateCartEntry(existingEntry, addCartEntryDto, product))
              .orElseGet(() -> addNewCartEntry(currentCart, addCartEntryDto, product))
              .flatMap(updatedEntry -> saveCartAndReturnEntry(currentCart, updatedEntry));
        });
  }

  private Optional<CartEntry> findExistingCartEntry(Cart currentCart, String productCode) {
    return currentCart.getEntries().stream()
        .filter(entry -> entry.getProduct().getCode().equals(productCode))
        .findFirst();
  }

  private Mono<CartEntry> updateCartEntry(CartEntry existingEntry, AddCartEntryDto addCartEntryDto, Product product) {
    existingEntry.setQuantity(addCartEntryDto.getQuantity());
    existingEntry.setPrice(product.getPrice().multiply(BigDecimal.valueOf(addCartEntryDto.getQuantity())));
    return Mono.just(existingEntry);
  }

  private Mono<CartEntry> addNewCartEntry(Cart currentCart, AddCartEntryDto addCartEntryDto, Product product) {
    CartEntry newCartEntry = CartEntry.builder()
        .product(product)
        .quantity(addCartEntryDto.getQuantity())
        .price(product.getPrice().multiply(BigDecimal.valueOf(addCartEntryDto.getQuantity())))
        .build();

    currentCart.getEntries().add(newCartEntry);
    return Mono.just(newCartEntry);
  }

  private Mono<CartEntry> saveCartAndReturnEntry(Cart currentCart, CartEntry cartEntry) {
    recalculateCartTotal(currentCart);
    return cartRepository.save(currentCart).thenReturn(cartEntry);
  }

  private Cart recalculateCartTotal(Cart cart) {
    BigDecimal newTotal = cart.getEntries().stream()
        .map(CartEntry::getPrice)
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    cart.setTotal(newTotal);
    return cart;
  }


  public Mono<Cart> removeEntry(String productCode) {
    return getCurrentCart().flatMap(currentCart -> {
      currentCart.getEntries().removeIf(cartEntry -> cartEntry.getProduct().getCode().equals(productCode));
      recalculateCartTotal(currentCart);
      return cartRepository.save(currentCart);
    });
  }
}

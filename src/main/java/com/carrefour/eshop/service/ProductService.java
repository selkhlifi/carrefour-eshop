package com.carrefour.eshop.service;

import com.carrefour.eshop.entity.Product;
import com.carrefour.eshop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Flux<Product> findAllProducts() {
    return productRepository.findAll();
  }

  public Mono<Product> findProductByCode(String code) {
    return productRepository.findByCode(code);
  }

}

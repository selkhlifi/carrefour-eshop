package com.carrefour.eshop.api;

import com.carrefour.eshop.api.assemblers.ProductModelAssembler;
import com.carrefour.eshop.dto.ProductDto;
import com.carrefour.eshop.exceptions.ProductNotFoundException;
import com.carrefour.eshop.mapper.ProductMapper;
import com.carrefour.eshop.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@Slf4j
public class ProductController {

  private final ProductService productService;
  private final ProductMapper productMapper;
  private final ProductModelAssembler productModelAssembler;

  public ProductController(ProductService productService, ProductMapper productMapper,
      ProductModelAssembler productModelAssembler) {
    this.productService = productService;
    this.productMapper = productMapper;
    this.productModelAssembler = productModelAssembler;
  }

  @Operation(summary = "Get a product by code", description = "Returns product details for the given product code.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved product"),
      @ApiResponse(responseCode = "404", description = "Product not found")
  })
  @GetMapping("/{code}")
  public Mono<EntityModel<ProductDto>> getProduct(@PathVariable String code, final ServerWebExchange exchange) {
    return productService
        .findProductByCode(code)
        .map(productMapper::toDto)
        .flatMap(productDto -> productModelAssembler.toModel(productDto, exchange))
        .switchIfEmpty(Mono.error(new ProductNotFoundException(code)));
  }


  @Operation(summary = "Get all available products", description = "Returns a list of all available products.")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved list of products")
  @GetMapping
  public Mono<CollectionModel<EntityModel<ProductDto>>> availableProducts(final ServerWebExchange exchange) {
    return productService.findAllProducts()
        .map(productMapper::toDto)
        .flatMap(product -> productModelAssembler.toModel(product, exchange))
        .collectList()
        .map(CollectionModel::of);
  }
}

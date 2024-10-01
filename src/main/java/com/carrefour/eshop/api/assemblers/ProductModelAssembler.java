package com.carrefour.eshop.api.assemblers;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

import com.carrefour.eshop.api.ProductController;
import com.carrefour.eshop.dto.ProductDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.reactive.ReactiveRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ProductModelAssembler implements ReactiveRepresentationModelAssembler<ProductDto, EntityModel<ProductDto>> {

  @Override
  public Mono<EntityModel<ProductDto>> toModel(ProductDto product, ServerWebExchange exchange) {
    // Create links using exchange for the current request context
    return linkTo(methodOn(ProductController.class).getProduct(product.getCode(), exchange))
        .withSelfRel()
        .toMono()
        .flatMap(selfLink -> linkTo(methodOn(ProductController.class).availableProducts(exchange))
            .withRel("all-products")
            .toMono()
            .map(allProductsLink -> EntityModel.of(product).add(selfLink, allProductsLink)));
  }

}

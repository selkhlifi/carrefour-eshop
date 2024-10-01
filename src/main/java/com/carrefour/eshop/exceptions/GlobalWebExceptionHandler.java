package com.carrefour.eshop.exceptions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;


@Component
@Order(-2)  // Ensures this handler takes precedence over other handlers
public class GlobalWebExceptionHandler implements WebExceptionHandler {

  private final ObjectMapper objectMapper;

  public GlobalWebExceptionHandler(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
    if (ex instanceof ExpiredJwtException) {
      ErrorResponse errorResponse = new ErrorResponse("Your session has expired. Please log in again.", HttpStatus.UNAUTHORIZED.value());
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

      try {
        byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
      } catch (JsonProcessingException e) {
        return Mono.error(e);
      }
    }

    return Mono.error(ex);
  }
}


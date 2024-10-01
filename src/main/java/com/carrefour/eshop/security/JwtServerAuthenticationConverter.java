package com.carrefour.eshop.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtServerAuthenticationConverter implements ServerAuthenticationConverter {

  private static final String BEARER_PREFIX = "Bearer ";

  @Override
  public Mono<Authentication> convert(ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("Authorization"))
        .filter(authHeader -> StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_PREFIX))
        .map(authHeader -> authHeader.substring(BEARER_PREFIX.length())) // Remove "Bearer " prefix
        .map(JwtAuthenticationToken::new); // Create a JwtAuthenticationToken with the extracted token
  }
}


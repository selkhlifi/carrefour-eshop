package com.carrefour.eshop.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class JwtSecurityContextRepository implements ServerSecurityContextRepository {

  private final JwtUtil jwtUtil;
  private final CustomerDetailsService customerDetailsService;

  public JwtSecurityContextRepository(JwtUtil jwtUtil, CustomerDetailsService customerDetailsService) {
    this.jwtUtil = jwtUtil;
    this.customerDetailsService = customerDetailsService;
  }

  @Override
  public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
    return Mono.empty();
  }

  @Override
  public Mono<SecurityContext> load(ServerWebExchange exchange) {
    return extractToken(exchange)
        .flatMap(token -> Mono.justOrEmpty(jwtUtil.extractUsername(token))) // Wrap extractUsername in Mono
        .flatMap(customerDetailsService::findByUsername)
        .map(userDetails -> new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities()))
        .map(SecurityContextImpl::new);
  }

  private Mono<String> extractToken(ServerWebExchange exchange) {
    return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("Authorization"))
        .filter(authHeader -> authHeader.startsWith("Bearer "))
        .map(authHeader -> authHeader.substring(7));
  }
}


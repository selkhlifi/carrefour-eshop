package com.carrefour.eshop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  private final JwtUtil jwtUtil;
  private final CustomerDetailsService customerDetailsService;

  public SecurityConfig(JwtUtil jwtUtil, CustomerDetailsService customerDetailsService) {
    this.jwtUtil = jwtUtil;
    this.customerDetailsService = customerDetailsService;
  }

  @Bean
  public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
    http
        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
        .csrf(ServerHttpSecurity.CsrfSpec::disable)
        .authorizeExchange(this::customizeAuthorizeExchange)
        .securityContextRepository(jwtSecurityContextRepository()) // Add custom SecurityContextRepository
        .addFilterAt(jwtAuthenticationWebFilter(), SecurityWebFiltersOrder.FIRST); // Add JWT filter

    return http.build();
  }

  private void customizeAuthorizeExchange(ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchangeSpec) {
    authorizeExchangeSpec
        .pathMatchers("/api/v1/auth/**").permitAll()  // Allow authentication endpoints without token
        .pathMatchers("/api-docs/**", "/api-docs/webjars/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Allow authentication endpoints without token
        .anyExchange().authenticated(); // Other endpoints require authentication
  }

  @Bean
  public ServerSecurityContextRepository jwtSecurityContextRepository() {
    return new JwtSecurityContextRepository(jwtUtil, customerDetailsService);
  }

  @Bean
  public AuthenticationWebFilter jwtAuthenticationWebFilter() {
    AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(authenticationManager());
    jwtFilter.setServerAuthenticationConverter(new JwtServerAuthenticationConverter()); // Use custom converter
    jwtFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/api/v1/products/**",
        "/api/v1/carts/**",
        "/api/v1/orders/**")); // Apply to API endpoints only
    return jwtFilter;
  }

  @Bean
  public ReactiveAuthenticationManager authenticationManager() {
    return new JwtReactiveAuthenticationManager(jwtUtil, customerDetailsService);
  }
}

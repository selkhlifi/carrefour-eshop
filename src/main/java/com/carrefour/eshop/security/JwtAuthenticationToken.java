package com.carrefour.eshop.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  private final String token;

  public JwtAuthenticationToken(String token) {
    super(null);
    this.token = token;
    setAuthenticated(false); // Initially not authenticated
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public Object getPrincipal() {
    return null; // No principal yet, it will be set by the authentication manager
  }
}


package com.carrefour.eshop.security;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

  private final JwtUtil jwtUtil;
  private final ReactiveUserDetailsService userDetailsService;

  public JwtReactiveAuthenticationManager(JwtUtil jwtUtil, ReactiveUserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    // Extract the token from the Authentication object
    String token = authentication.getCredentials().toString();

    // Extract username from the token before validating
    String username;

    username = jwtUtil.extractUsername(token); // Extract the username from the token

    // Validate the JWT token using the username or user details (adjust as necessary)
    if (!jwtUtil.validateToken(token, username)) {
      return Mono.empty(); // TODO return InvalidTokenException instead + add a Exception handler for it
    }

    // Retrieve user details based on the extracted username
    return userDetailsService.findByUsername(username)
        .map(userDetails -> {
          // Create a new JwtAuthenticationToken with just the token (as required by the constructor)
          JwtAuthenticationToken auth = new JwtAuthenticationToken(token);

          // Optionally, associate authorities or other details with the token
          auth.setDetails(userDetails);
          auth.setAuthenticated(true); // Set as authenticated since validation succeeded
          return auth;
        });
  }


}


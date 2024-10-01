package com.carrefour.eshop.security;

import com.carrefour.eshop.service.CustomerService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CustomerDetailsService implements ReactiveUserDetailsService {

  private final CustomerService customerService;

  public CustomerDetailsService(CustomerService customerService) {
    this.customerService = customerService;
  }

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return customerService.findByUsername(username).map(customer -> User.withUsername(username)
        .password(
            "{noop}password") // {noop} is a password encoder that stores passwords in plain text (for demo only)
        .roles("CUSTOMER_ROLE")
        .build());
  }
}


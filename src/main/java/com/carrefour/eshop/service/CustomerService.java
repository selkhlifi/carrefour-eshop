package com.carrefour.eshop.service;

import com.carrefour.eshop.entity.Customer;
import com.carrefour.eshop.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public Mono<Customer> getCurrentCustomer() {
    return getCurrentUsername().flatMap(customerRepository::findByUsername);
  }

  public Mono<String> getCurrentUsername() {
    return ReactiveSecurityContextHolder.getContext()
        .map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated)
        .map(authentication -> {
          String username = ((User)authentication.getDetails()).getUsername();
          log.info("Current user: {}", username);  // Log the username
          return username;
        });
  }

  public Mono<Customer> findByUsername(String username) {
    return customerRepository.findByUsername(username);
  }
}

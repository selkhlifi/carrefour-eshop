package com.carrefour.eshop.api;

import com.carrefour.eshop.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JwtUtil jwtUtil;

  public AuthController(final JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @PostMapping("/login")
  public Mono<ResponseEntity<String>> login(@RequestParam String username, @RequestParam String password) {
    String token = jwtUtil.generateToken(username);
    return Mono.just(ResponseEntity.ok("Bearer " + token));
  }

}

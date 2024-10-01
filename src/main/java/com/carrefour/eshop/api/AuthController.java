package com.carrefour.eshop.api;

import com.carrefour.eshop.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/auth")
public class AuthController {

  private final JwtUtil jwtUtil;

  public AuthController(final JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Operation(summary = "Authenticate user and generate JWT token", description = "This endpoint generates a JWT token for a valid user login.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful login",
          content = @Content(mediaType = "application/json",
              schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized, invalid credentials",
          content = @Content(mediaType = "application/json"))
  })
  @PostMapping("/login")
  public Mono<ResponseEntity<String>> login(@RequestParam String username, @RequestParam String password) {
    String token = jwtUtil.generateToken(username);
    return Mono.just(ResponseEntity.ok("Bearer " + token));
  }
}

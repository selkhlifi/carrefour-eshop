package com.carrefour.eshop.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

  private static final long TOKEN_VALIDITY = 1000 * 60 * 60 * 10; // 10 hours

  private final SecretKey secretKey;

  public JwtUtil(@Value("${jwt.secret}") String base64Secret) {
    this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Secret));
  }

  // Method to generate a JWT token
  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
        .signWith(secretKey) // Use the secure key here
        .compact();
  }

  // Retrieve username from JWT token
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // Extract a specific claim from the token
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  // Check if the token has expired
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // Extract expiration date from token
  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Validate if the token is valid
  public Boolean validateToken(String token, String username) {
    final String extractedUsername = extractUsername(token);
    return (extractedUsername.equals(username) && !isTokenExpired(token));
  }

  private Claims getAllClaimsFromToken(String token) {
    // Create a JwtParser using the secure SecretKey
    return Jwts.parserBuilder() // Use parserBuilder for a modern approach
        .setSigningKey(secretKey)
        .build() // Build the parser
        .parseClaimsJws(token)
        .getBody();
  }
}

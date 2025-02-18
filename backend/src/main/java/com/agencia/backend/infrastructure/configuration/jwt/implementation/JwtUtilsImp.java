package com.agencia.backend.infrastructure.configuration.jwt.implementation;

import com.agencia.backend.infrastructure.configuration.jwt.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUtilsImp implements JwtUtils {

  @Value(value = "${spring.app.jwtSecret}")
  private String jwtSecret;

  @Value("${spring.app.jwt.issuer}")
  private String jwtIssuer;

  @Value("${spring.app.jwt.audience}")
  private String jwtAudience;


  public String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
      return null;
    }

    return bearerToken.substring(7); // Remove Bearer prefix
  }

  public String generateToken(UserDetails userDetails) {
    Instant now = Instant.now();

    return Jwts.builder()
        .header()
        .add("typ", "JWT")
        .add("alg", "HS512") // Header explícito
        .and()
        .subject(userDetails.getUsername())
        .issuer(jwtIssuer)
        .audience().add(jwtAudience).and()
        .claim("roles", userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList()))
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plus(15, ChronoUnit.MINUTES)))
        .id(UUID.randomUUID().toString())
        .signWith(key())
        .compact();
  }

  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser()
        .verifyWith((SecretKey) key())
        .build().parseSignedClaims(token)
        .getPayload().getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser()
          .verifyWith((SecretKey) key())
          .requireIssuer(jwtIssuer)
          .requireAudience(jwtAudience)
          .build()
          .parseSignedClaims(authToken);
      return true;
    }
    catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      return false;
    }
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

}

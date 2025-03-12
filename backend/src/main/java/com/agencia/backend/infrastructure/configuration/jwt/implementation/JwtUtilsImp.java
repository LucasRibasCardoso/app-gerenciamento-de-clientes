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
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUtilsImp implements JwtUtils {

  public String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");

    if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
      return null;
    }

    return bearerToken.substring(7); // Remove Bearer prefix
  }

  public String generateTokenFromUsername(UserDetails userDetails) {
    String username = userDetails.getUsername();

    Instant now = Instant.now();
    Instant expirationTime = now.plus(1, ChronoUnit.DAYS); // 1 dia de validade

    String token = Jwts.builder()
        .subject(username)
        .issuedAt(Date.from(now))
        .expiration((Date.from(expirationTime)))
        .signWith(key())
        .compact();

    return token;
  }

  public String getUsernameFromJwtToken(String token) {
    return Jwts.parser()
        .verifyWith((SecretKey) key())
        .build().parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  public boolean validateJwtToken(String authToken) {
    try {
      Jwts.parser()
          .verifyWith((SecretKey) key())
          .build()
          .parseSignedClaims(authToken);
      return true;
    }
    catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
      return false;
    }
  }

  private Key key() {
    String jwt_key = System.getenv("JWT_KEY");
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwt_key));
  }

}

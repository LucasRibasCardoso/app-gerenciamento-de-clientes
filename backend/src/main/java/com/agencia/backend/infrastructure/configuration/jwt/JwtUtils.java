package com.agencia.backend.infrastructure.configuration.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtUtils {
  String getJwtFromHeader(HttpServletRequest request);
  String generateToken(UserDetails userDetails);
  String getUsernameFromJwtToken(String token);
  boolean validateJwtToken(String authToken);
}

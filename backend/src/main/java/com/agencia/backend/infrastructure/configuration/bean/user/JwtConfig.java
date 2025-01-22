package com.agencia.backend.infrastructure.configuration.bean.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.infrastructure.configuration.jwt.AuthTokenFilter;
import com.agencia.backend.infrastructure.configuration.jwt.implementation.AuthEntryPointJwtImp;
import com.agencia.backend.infrastructure.configuration.jwt.JwtUtils;
import com.agencia.backend.infrastructure.configuration.jwt.implementation.JwtUtilsImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
public class JwtConfig {

  @Bean
  public JwtUtils jwtUtils() {
    return new JwtUtilsImp();
  }

  @Bean
  public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
    return new AuthEntryPointJwtImp(objectMapper);
  }

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter(
      JwtUtils jwtUtils,
      UserDetailsService userDetailsService
  ) {
    return new AuthTokenFilter(jwtUtils, userDetailsService);
  }

}

package com.agencia.backend.infrastructure.configuration.jwt.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.presentation.dto.error.GenericError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Implementação da interface AuthenticationEntryPoint do SpringBoot.
 * É utilizada para lidar com exceções de autenticação.
 */
public class AuthEntryPointJwtImp implements AuthenticationEntryPoint {

  private final ObjectMapper objectMapper;

  public AuthEntryPointJwtImp(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException
  {
    // Definir o tipo de conteúdo da resposta
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

    // Criando o corpo da resposta JSON
    GenericError responseError = new GenericError(
        "Falha ao efetuar login. Tente novamente.",
        HttpStatus.UNAUTHORIZED.value()
    );

    objectMapper.writeValue(response.getOutputStream(), responseError);
  }

}

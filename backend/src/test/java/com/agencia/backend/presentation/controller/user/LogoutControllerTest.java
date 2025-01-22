package com.agencia.backend.presentation.controller.user;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.application.useCase.user.RegisterUserUseCase;
import com.agencia.backend.infrastructure.configuration.jwt.JwtUtils;
import com.agencia.backend.presentation.controller.LoginController;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(LoginController.class)
public class LogoutControllerTest {

  @MockitoBean
  private RegisterUserUseCase createUserUseCase;

  @MockitoBean
  private UserMapper userMapper;

  @MockitoBean
  private ValidateUserRequest validateUserRequest;

  @MockitoBean
  private AuthenticationManager authenticationManager;

  @MockitoBean
  private JwtUtils jwtUtils;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void testLogout() throws Exception {
    // Act & Assert
    mockMvc.perform(post("/auth/logout")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

    // Verifica se o SecurityContext foi limpo
    assertNull(SecurityContextHolder.getContext().getAuthentication());
  }
}

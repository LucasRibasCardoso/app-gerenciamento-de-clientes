package com.agencia.backend.presentation.controller.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.application.useCase.user.RegisterUserUseCase;
import com.agencia.backend.infrastructure.configuration.jwt.JwtUtils;
import com.agencia.backend.presentation.controller.LoginController;
import com.agencia.backend.presentation.dto.user.LoginRequestDTO;
import com.agencia.backend.presentation.dto.user.LoginResponseDTO;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(LoginController.class)
public class LoginControllerTest {

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
  void ShouldLoginSuccess() throws Exception {
    // Arrange
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO("userCommon", "12345678@");
    LoginResponseDTO loginResponseDTO = new LoginResponseDTO("userCommon", List.of("ROLE_USER"), "mocked-jwt-token");

    UserDetails userDetails = User.builder()
        .username("userCommon")
        .password("12345678@")
        .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
        .build();

    UsernamePasswordAuthenticationToken authentication =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

    when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
    when(jwtUtils.generateTokenFromUsername(userDetails)).thenReturn("mocked-jwt-token");

    String requestBody = objectMapper.writeValueAsString(loginRequestDTO);
    String responseBody = objectMapper.writeValueAsString(loginResponseDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().json(responseBody));
  }

  @Test
  void ShouldThrowException_WhenInvalidCredentials() throws Exception {
    // Arrange
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO("userCommon", "wrongPassword");

    when(authenticationManager.authenticate(any(Authentication.class)))
        .thenThrow(new BadCredentialsException("Usuário ou senha inválidos."));

    String requestBody = objectMapper.writeValueAsString(loginRequestDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isUnauthorized())
        .andExpect(jsonPath("$.message").value("Usuário ou senha inválidos."))
        .andExpect(jsonPath("$.statusCode").value("401"));
  }

  @Test
  void ShouldThrowException_WhenInvalidLoginRequest() throws Exception {
    // Arrange
    LoginRequestDTO loginRequestDTO = new LoginRequestDTO(null, "1234567");

    String requestBody = objectMapper.writeValueAsString(loginRequestDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(content().json("""
         {
           "message": "Erro de validação nos campos enviados",
           "statusCode": 400,
             "errors": [
               { "field": "username", "message": "O username precisa ser informado" },
               { "field": "password", "message": "A senha deve ter entre 8 e 20 caracteres" }
             ]
         }
         """));

  }
}

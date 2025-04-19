package com.agencia.backend.presentation.controller;

import com.agencia.backend.infrastructure.configuration.jwt.JwtUtils;
import com.agencia.backend.infrastructure.configuration.log4jConfig.ApplicationLogger;
import com.agencia.backend.presentation.dto.user.LoginRequestDTO;
import com.agencia.backend.presentation.dto.user.LoginResponseDTO;
import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

  private final ApplicationLogger applicationLogger;
  private final ValidateUserRequest validateUserRequest;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  public LoginController(
      ApplicationLogger applicationLogger,
      ValidateUserRequest validateUserRequest,
      AuthenticationManager authenticationManager,
      JwtUtils jwtUtils
  ) {
    this.applicationLogger = applicationLogger;
    this.validateUserRequest = validateUserRequest;
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
    validateUserRequest.validateUsername(dto.username());
    validateUserRequest.validatePassword(dto.password());

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    String jwtToken = jwtUtils.generateToken(userDetails);

    // Extrai a primeira (e única) autoridade como uma string
    String role = userDetails.getAuthorities().stream()
        .findFirst()
        .map(item -> item.getAuthority())
        .orElse("ROLE_USER"); // Valor padrão caso não tenha role

    LoginResponseDTO response = new LoginResponseDTO(userDetails.getUsername(), role, jwtToken);

    applicationLogger.logLogin(dto.username());

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    SecurityContextHolder.clearContext();

    return ResponseEntity.noContent().build();
  }
}

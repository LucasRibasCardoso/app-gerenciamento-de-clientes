package com.agencia.backend.presentation.controller;

import com.agencia.backend.domain.entity.User;
import com.agencia.backend.infrastructure.configuration.jwt.JwtUtils;
import com.agencia.backend.presentation.dto.user.LoginRequestDTO;
import com.agencia.backend.presentation.dto.user.LoginResponseDTO;
import com.agencia.backend.presentation.dto.user.UserRequestDTO;
import com.agencia.backend.presentation.dto.user.UserResponseDTO;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.application.useCase.user.RegisterUserUseCase;
import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class LoginController {

  private final RegisterUserUseCase createUserUseCase;
  private final UserMapper userMapper;
  private final ValidateUserRequest validateUserRequest;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;

  public LoginController(
      RegisterUserUseCase createUserUseCase,
      UserMapper userMapper,
      ValidateUserRequest validateUserRequest,
      AuthenticationManager authenticationManager,
      JwtUtils jwtUtils
  ) {
    this.createUserUseCase = createUserUseCase;
    this.userMapper = userMapper;
    this.validateUserRequest = validateUserRequest;
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/register")
  public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
    validateUserRequest.validateUsername(dto.username());
    validateUserRequest.validatePassword(dto.password());
    validateUserRequest.validateRoles(dto.roles());

    User userSaved = createUserUseCase.register(userMapper.toDomain(dto));

    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(userSaved.getId())
        .toUri();

    return ResponseEntity.status(HttpStatus.CREATED).location(location).body(userMapper.toDTO(userSaved));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
    );

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    LoginResponseDTO response = new LoginResponseDTO(userDetails.getUsername(), roles, jwtToken);

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/logout")
  public ResponseEntity<Void> logout() {
    SecurityContextHolder.clearContext();
    return ResponseEntity.noContent().build();
  }
}

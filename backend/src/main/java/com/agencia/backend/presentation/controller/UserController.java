package com.agencia.backend.presentation.controller;

import com.agencia.backend.application.useCase.user.FindUserByIdUseCase;
import com.agencia.backend.application.useCase.user.RegisterUserUseCase;
import com.agencia.backend.application.useCase.user.UpdateUserUseCase;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.presentation.dto.user.UserRequestDTO;
import com.agencia.backend.presentation.dto.user.UserResponseDTO;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.application.useCase.user.DeleteUserUseCase;
import com.agencia.backend.application.useCase.user.FindAllUserUseCase;
import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

  private final RegisterUserUseCase createUserUseCase;
  private final UserMapper userMapper;
  private final FindAllUserUseCase findAllUserUseCase;
  private final FindUserByIdUseCase findUserByIdUseCase;
  private final DeleteUserUseCase deleteUserUseCase;
  private final ValidateUserRequest validateUserRequest;
  private final UpdateUserUseCase updateUserUseCase;

  public UserController(
      RegisterUserUseCase createUserUseCase,
      UserMapper userMapper,
      FindAllUserUseCase findAllUserUseCase,
      FindUserByIdUseCase findUserByIdUseCase,
      DeleteUserUseCase deleteUserUseCase,
      ValidateUserRequest validateUserRequest,
      UpdateUserUseCase updateUserUseCase
  ) {
    this.createUserUseCase = createUserUseCase;
    this.userMapper = userMapper;
    this.findAllUserUseCase = findAllUserUseCase;
    this.findUserByIdUseCase = findUserByIdUseCase;
    this.deleteUserUseCase = deleteUserUseCase;
    this.validateUserRequest = validateUserRequest;
    this.updateUserUseCase = updateUserUseCase;
  }

  // CREATE
  @PostMapping("/register")
  public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
    // Valida os dados de entrada
    validateUserRequest.validateUsername(dto.username());
    validateUserRequest.validatePassword(dto.password());
    validateUserRequest.validateRole(dto.role());

    // Salva o usuário
    User userSaved = createUserUseCase.register(userMapper.toDomain(dto));

    // Cria a URI de localização do novo recurso
    URI location = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(userSaved.getId())
        .toUri();

    // Retorna a resposta
    return ResponseEntity.status(HttpStatus.CREATED)
        .location(location)
        .body(userMapper.toDTO(userSaved));
  }

  // READ
  @GetMapping()
  public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
    List<UserResponseDTO> users = findAllUserUseCase.getAllUsers()
        .stream()
        .map(userMapper::toDTO)
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK)
        .body(users);
  }

  // READ
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> findUserById(@PathVariable String id) {
    // Valida o UUID
    validateUserRequest.validateUUID(id);

    // Busca o usuário
    User user = findUserByIdUseCase.getUser(UUID.fromString(id));

    // Retorna a resposta
    return ResponseEntity.status(HttpStatus.OK)
        .body(userMapper.toDTO(user));
  }


  // UPDATE
  @PutMapping("/{id}")
  public ResponseEntity<UserResponseDTO> updateUser(
      @PathVariable String id, @Valid @RequestBody UserRequestDTO dto
  ) {

    // Valida o UUID
    validateUserRequest.validateUUID(id);

    // Valida apenas os campos não nulos
    if (dto.username() != null) {
      validateUserRequest.validateUsername(dto.username());
    }
    if (dto.password() != null) {
      validateUserRequest.validatePassword(dto.password());
    }
    if (dto.role() != null) {
      validateUserRequest.validateRole(dto.role());
    }

    // Atualiza o usuário
    User updatedUser = updateUserUseCase.update(UUID.fromString(id), userMapper.toDomain(dto));

    // Retorna a resposta
    return ResponseEntity.status(HttpStatus.OK)
        .body(userMapper.toDTO(updatedUser));
  }


  // DELETE
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable String id) {
    validateUserRequest.validateUUID(id);
    deleteUserUseCase.deleteUser(UUID.fromString(id));
    return ResponseEntity.noContent()
        .build();
  }

}

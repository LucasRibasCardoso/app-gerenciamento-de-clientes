package com.agencia.backend.presentation.controller;

import com.agencia.backend.presentation.dto.user.UserResponseDTO;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.application.useCase.user.DeleteUserUseCase;
import com.agencia.backend.application.useCase.user.FindAllUserUseCase;
import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserMapper userMapper;
  private final FindAllUserUseCase findAllUserUseCase;
  private final DeleteUserUseCase deleteUserUseCase;
  private final ValidateUserRequest validateUserRequest;

  public UserController(
      UserMapper userMapper,
      FindAllUserUseCase findAllUserUseCase,
      DeleteUserUseCase deleteUserUseCase,
      ValidateUserRequest validateUserRequest
  ) {
    this.userMapper = userMapper;
    this.findAllUserUseCase = findAllUserUseCase;
    this.deleteUserUseCase = deleteUserUseCase;
    this.validateUserRequest = validateUserRequest;
  }

  @GetMapping()
  public ResponseEntity<List<UserResponseDTO>> findAllUsers() {
    List<UserResponseDTO> users = findAllUserUseCase.getAllUsers().stream()
        .map(userMapper::toDTO)
        .collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(users);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable String id)
  {
    validateUserRequest.validateUUID(id);
    deleteUserUseCase.deleteUser(UUID.fromString(id));
    return ResponseEntity.noContent().build();
  }

}

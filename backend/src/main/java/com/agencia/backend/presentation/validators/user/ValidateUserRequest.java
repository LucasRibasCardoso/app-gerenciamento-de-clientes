package com.agencia.backend.presentation.validators.user;

public interface ValidateUserRequest {
  void validateUsername(String username);
  void validateRole(String role);
  void validatePassword(String password);
  void validateUUID(String uuid);
}

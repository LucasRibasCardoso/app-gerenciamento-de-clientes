package com.agencia.backend.presentation.validators.user;

import java.util.Set;

public interface ValidateUserRequest {
  void validateUsername(String username);
  void validateRoles(Set<String> roles);
  void validatePassword(String password);
  void validateUUID(String uuid);
}

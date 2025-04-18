package com.agencia.backend.application.services.implementation;

import com.agencia.backend.application.services.UserFieldUpdateService;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;

public class UserFieldUpdateServiceImp implements UserFieldUpdateService {

  private String updateUsername(String existingUsername, String newUsername) {
    return newUsername != null ? newUsername : existingUsername;
  }

  private Role updateRoles(Role existingRole, Role newRole) {
    return newRole != null ? newRole : existingRole;
  }

  @Override
  public User updateUser(User existingUser, User userRequest) {
    return new User(
        existingUser.getId(), // ID não pode ser alterado
        updateUsername(existingUser.getUsername(), userRequest.getUsername()),
        existingUser.getPassword(), // A senha não pode ser alterada
        updateRoles(existingUser.getRole(), userRequest.getRole())
    );
  }

}

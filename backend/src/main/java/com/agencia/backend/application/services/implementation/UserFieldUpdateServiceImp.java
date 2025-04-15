package com.agencia.backend.application.services.implementation;

import com.agencia.backend.application.services.UserFieldUpdateService;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFieldUpdateServiceImp implements UserFieldUpdateService {

  private final PasswordEncoder passwordEncoder;
  public UserFieldUpdateServiceImp(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  private String updateUsername(String existingUsername, String newUsername) {
    return newUsername != null ? newUsername : existingUsername;
  }

  private String updatePassword(String existingPassword, String newPassword) {
    String password = newPassword != null ? newPassword : existingPassword;
    return passwordEncoder.encode(password);
  }

  private Role updateRoles(Role existingRole, Role newRole) {
    return newRole != null ? newRole : existingRole;
  }

  @Override
  public User updateUser(User existingUser, User userRequest) {
    return new User(
        existingUser.getId(), // ID não pode ser alterado
        updateUsername(existingUser.getUsername(), userRequest.getUsername()),
        updatePassword(existingUser.getPassword(), userRequest.getPassword()),
        updateRoles(existingUser.getRole(), userRequest.getRole())
    );
  }

}

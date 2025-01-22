package com.agencia.backend.presentation.validators.user.implementation;

import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.domain.exceptions.user.InvalidPasswordException;
import com.agencia.backend.domain.exceptions.user.InvalidRoleException;
import com.agencia.backend.domain.exceptions.user.InvalidUUIDException;
import com.agencia.backend.domain.exceptions.user.InvalidUsernameException;
import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

public class ValidateUserRequestImp implements ValidateUserRequest {

  private static final String SPECIAL_CHARACTER_PATTERN = "[^A-Za-z0-9]";
  private static final String START_WITH_LETTER_PATTERN = "^[A-Za-z]";

  @Override
  public void validatePassword(String password) {
    if (!Pattern.compile(SPECIAL_CHARACTER_PATTERN).matcher(password).find()) {
      throw new InvalidPasswordException("A senha deve conter pelo menos um caractere especial.");
    }
  }

  @Override
  public void validateRoles(Set<String> roles) {
    if (roles == null || roles.isEmpty()) {
      throw new InvalidRoleException("A lista de permissões não pode estar vazia ou nula.");
    }

    try {
      roles.forEach(role -> Role.valueOf(role));
    }
    catch (IllegalArgumentException e) {
      throw new InvalidRoleException("Permissões inválidas: " + roles);
    }
  }

  @Override
  public void validateUsername(String username) {
    if (!Pattern.compile(START_WITH_LETTER_PATTERN).matcher(username).find()) {
      throw new InvalidUsernameException("O nome de usuário deve começar com uma letra.");
    }

    if (Pattern.compile(SPECIAL_CHARACTER_PATTERN).matcher(username).find()) {
      throw new InvalidUsernameException("O nome de usuário não pode conter caracteres especiais.");
    }
  }

  @Override
  public void validateUUID(String input) {
    try {
      UUID.fromString(input);
    }
    catch (IllegalArgumentException e) {
      throw new InvalidUUIDException("O id do usuário fornecido não é válido.");
    }
  }

}

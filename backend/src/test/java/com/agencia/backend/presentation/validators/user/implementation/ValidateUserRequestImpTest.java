package com.agencia.backend.presentation.validators.user.implementation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.agencia.backend.domain.exceptions.user.InvalidPasswordException;
import com.agencia.backend.domain.exceptions.user.InvalidRoleException;
import com.agencia.backend.domain.exceptions.user.InvalidUUIDException;
import com.agencia.backend.domain.exceptions.user.InvalidUsernameException;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ValidateUserRequestImpTest {

  private ValidateUserRequestImp validateUserRequestImp = new ValidateUserRequestImp();

  @Test
  void ShouldValidatePasswordSuccessfully() {
    // Arrange
    String password = "Password123!";

    // Act & Assert
    assertDoesNotThrow(() -> validateUserRequestImp.validatePassword(password));
  }

  @Test
  void ShouldThrowException_WhenPasswordDoesNotContainSpecialCharacter() {
    // Arrange
    String password = "Password123";

    // Act & Assert
    InvalidPasswordException exception = assertThrows(
        InvalidPasswordException.class, () -> validateUserRequestImp.validatePassword(password));

    assertEquals("A senha deve conter pelo menos um caractere especial.", exception.getMessage());
  }

  @Test
  void ShouldValidateRolesSuccessfully() {
    // Arrange
    Set<String> roles = Set.of("USER", "ADMIN");

    // Act & Assert
    assertDoesNotThrow(() -> validateUserRequestImp.validateRoles(roles));
  }

  @Test
  void ShouldThrowException_WhenRoleIsNull(){
    // Arrange
    Set<String> roles = null;

    // Act & Assert
    InvalidRoleException exception = assertThrows(
        InvalidRoleException.class, () -> validateUserRequestImp.validateRoles(roles));

    assertEquals("A lista de permissões não pode estar vazia ou nula.", exception.getMessage());
  }

  @Test
  void ShouldThrowException_WhenRoleIsInvalid() {
    // Arrange
    Set<String> roles = Set.of("INVALID_ROLE");

    // Act & Assert
    InvalidRoleException exception = assertThrows(
        InvalidRoleException.class, () -> validateUserRequestImp.validateRoles(roles));

    assertEquals("Permissões inválidas: [INVALID_ROLE]", exception.getMessage());
  }

  @Test
  void ShouldValidateUsernameSuccessfully() {
    // Arrange
    String username = "JohnDoe";

    // Act & Assert
    assertDoesNotThrow(() -> validateUserRequestImp.validateUsername(username));
  }

  @Test
  void ShouldThrowException_WhenUsernameDoesNotStartWithLetter() {
    // Arrange
    String username = "123JohnDoe";

    // Act & Assert
    InvalidUsernameException exception = assertThrows(
        InvalidUsernameException.class, () -> validateUserRequestImp.validateUsername(username));

    assertEquals("O nome de usuário deve começar com uma letra.", exception.getMessage());
  }

  @Test
  void ShouldThrowException_WhenUsernameContainsSpecialCharacter() {
    // Arrange
    String username = "JohnDoe@";

    // Act & Assert
    InvalidUsernameException exception = assertThrows(
        InvalidUsernameException.class, () -> validateUserRequestImp.validateUsername(username));

    assertEquals("O nome de usuário não pode conter caracteres especiais.", exception.getMessage());
  }

  @Test
  void ShouldValidateUUIDSuccessfully() {
    // Arrange
    String uuid = "123e4567-e89b-12d3-a456-426614174000";

    // Act & Assert
    assertDoesNotThrow(() -> validateUserRequestImp.validateUUID(uuid));
  }

  @Test
  void ShouldThrowException_WhenUUIDIsInvalid() {
    // Arrange
    String uuid = "invalid-uuid";

    // Act & Assert
    InvalidUUIDException exception = assertThrows(
        InvalidUUIDException.class, () -> validateUserRequestImp.validateUUID(uuid));

    assertEquals("O id do usuário fornecido não é válido.", exception.getMessage());
  }
}
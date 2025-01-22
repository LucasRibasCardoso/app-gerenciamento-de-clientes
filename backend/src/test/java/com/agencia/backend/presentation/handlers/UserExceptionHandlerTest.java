package com.agencia.backend.presentation.handlers;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.agencia.backend.domain.exceptions.user.InvalidPasswordException;
import com.agencia.backend.domain.exceptions.user.InvalidRoleException;
import com.agencia.backend.domain.exceptions.user.InvalidUUIDException;
import com.agencia.backend.domain.exceptions.user.InvalidUsernameException;
import com.agencia.backend.domain.exceptions.user.UserAlreadyExistsException;
import com.agencia.backend.domain.exceptions.user.UserNotFoundException;
import com.agencia.backend.presentation.dto.error.GenericError;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class UserExceptionHandlerTest {
  private UserExceptionHandler handler = new UserExceptionHandler();

  @Test
  void HandlerUserNotFoundException() {
    // Arrange
    UserNotFoundException exception = new UserNotFoundException("Nenhum usuário encontrado");

    // Act
    ResponseEntity<GenericError> response = handler.handleUserNotFoundException(exception);

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(404, response.getStatusCode().value()),
        () -> assertEquals(404, response.getBody().statusCode()),
        () -> assertEquals("Nenhum usuário encontrado", response.getBody().message())
    );
  }

  @Test
  void HandlerUserAlreadyExistsException() {
    // Arrange
    UserAlreadyExistsException exception = new UserAlreadyExistsException("Usuário já existente");

    // Act
    ResponseEntity<GenericError> response = handler.handlerUserBadRequest(exception);

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals("Usuário já existente", response.getBody().message())
    );
  }

  @Test
  void HandlerInvalidPasswordException() {
    // Arrange
    InvalidPasswordException exception = new InvalidPasswordException("Senha inválida");

    // Act
    ResponseEntity<GenericError> response = handler.handlerUserBadRequest(exception);

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals("Senha inválida", response.getBody().message())
    );
  }

  @Test
  void HandlerInvalidUsernameException() {
    // Arrange
    InvalidUsernameException exception = new InvalidUsernameException("Username inválido");

    // Act
    ResponseEntity<GenericError> response = handler.handlerUserBadRequest(exception);

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals("Username inválido", response.getBody().message())
    );
  }

  @Test
  void HandlerInvalidRoleException() {
    // Arrange
    InvalidRoleException exception = new InvalidRoleException("Role inválida");

    // Act
    ResponseEntity<GenericError> response = handler.handlerUserBadRequest(exception);

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals("Role inválida", response.getBody().message())
    );
  }

  @Test
  void HandlerInvalidUUIDException() {
    // Arrange
    InvalidUUIDException exception = new InvalidUUIDException("UUID inválido");

    // Act
    ResponseEntity<GenericError> response = handler.handlerUserBadRequest(exception);

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals("UUID inválido", response.getBody().message())
    );
  }

  @Test
  void HandlerBadCredentialsException() {
    // Act
    ResponseEntity<GenericError> response = handler.handleBadCredentialsException();

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(401, response.getStatusCode().value()),
        () -> assertEquals(401, response.getBody().statusCode()),
        () -> assertEquals("Username ou senha inválidos.", response.getBody().message())
    );
  }
}

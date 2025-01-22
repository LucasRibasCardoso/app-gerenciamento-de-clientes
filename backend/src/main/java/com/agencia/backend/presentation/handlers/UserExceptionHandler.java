package com.agencia.backend.presentation.handlers;

import com.agencia.backend.presentation.dto.error.GenericError;
import com.agencia.backend.domain.exceptions.user.InvalidPasswordException;
import com.agencia.backend.domain.exceptions.user.InvalidRoleException;
import com.agencia.backend.domain.exceptions.user.InvalidUUIDException;
import com.agencia.backend.domain.exceptions.user.InvalidUsernameException;
import com.agencia.backend.domain.exceptions.user.UserAlreadyExistsException;
import com.agencia.backend.domain.exceptions.user.UserNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(1)
public class UserExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<GenericError> handleUserNotFoundException(UserNotFoundException ex) {
    GenericError error = new GenericError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler({
      UserAlreadyExistsException.class,
      InvalidPasswordException.class,
      InvalidUsernameException.class,
      InvalidRoleException.class,
      InvalidUUIDException.class,
  })
  public ResponseEntity<GenericError> handlerUserBadRequest(Exception ex) {
    GenericError error = new GenericError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<GenericError> handleBadCredentialsException() {
    GenericError error = new GenericError("Username ou senha inválidos.", HttpStatus.UNAUTHORIZED.value());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
  }

}
package com.agencia.backend.domain.exceptions.user;

public class InvalidUsernameException extends RuntimeException {

  public InvalidUsernameException(String message) {
    super(message);
  }

}

package com.agencia.backend.domain.exceptions.user;

public class InvalidPasswordException extends RuntimeException {

  public InvalidPasswordException(String message) {
    super(message);
  }

}

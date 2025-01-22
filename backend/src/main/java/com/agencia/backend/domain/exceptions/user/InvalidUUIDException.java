package com.agencia.backend.domain.exceptions.user;

public class InvalidUUIDException extends RuntimeException {

  public InvalidUUIDException(String message) {
    super(message);
  }

}

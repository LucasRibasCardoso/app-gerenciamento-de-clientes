package com.agencia.backend.domain.exceptions.client;

public class InvalidClientIdException extends RuntimeException {

  public InvalidClientIdException(String message) {
    super(message);
  }

}

package com.agencia.backend.domain.exceptions.global;

public class DatabaseException extends RuntimeException {

  public DatabaseException(String message) {
    super(message);
  }

}

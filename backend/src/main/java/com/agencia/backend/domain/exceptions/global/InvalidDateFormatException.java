package com.agencia.backend.domain.exceptions.global;

public class InvalidDateFormatException extends RuntimeException {

  public InvalidDateFormatException(String message) {
    super(message);
  }

}

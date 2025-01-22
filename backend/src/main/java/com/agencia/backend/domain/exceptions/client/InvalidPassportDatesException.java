package com.agencia.backend.domain.exceptions.client;

public class InvalidPassportDatesException extends RuntimeException {

  public InvalidPassportDatesException(String message) {
    super(message);
  }

}

package com.agencia.backend.domain.exceptions.client;

public class InvalidBirthDateClientException extends RuntimeException {

  public InvalidBirthDateClientException(String message) {
    super(message);
  }

}

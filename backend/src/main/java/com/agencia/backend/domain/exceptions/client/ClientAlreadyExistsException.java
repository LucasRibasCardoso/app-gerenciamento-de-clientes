package com.agencia.backend.domain.exceptions.client;

public class ClientAlreadyExistsException extends RuntimeException {

  public ClientAlreadyExistsException(String message) {
    super(message);
  }

}

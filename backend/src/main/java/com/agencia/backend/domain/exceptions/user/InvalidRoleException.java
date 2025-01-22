package com.agencia.backend.domain.exceptions.user;

public class InvalidRoleException extends RuntimeException {

  public InvalidRoleException(String message) {
    super(message);
  }

}

package com.agencia.backend.presentation.handlers;

import com.agencia.backend.domain.exceptions.client.ClientAlreadyExistsException;
import com.agencia.backend.domain.exceptions.client.ClientNotFoundException;
import com.agencia.backend.domain.exceptions.client.InvalidClientIdException;
import com.agencia.backend.domain.exceptions.client.InvalidPassportDatesException;
import com.agencia.backend.domain.exceptions.client.InvalidSortingParameterException;
import com.agencia.backend.domain.exceptions.global.InvalidDateFormatException;
import com.agencia.backend.presentation.dto.error.GenericError;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(2)
public class ClientExceptionHandler {

  @ExceptionHandler(ClientNotFoundException.class)
  public ResponseEntity<GenericError> handleClientNotFoundException(ClientNotFoundException ex) {
    GenericError error = new GenericError(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  @ExceptionHandler({
      InvalidPassportDatesException.class,
      ClientAlreadyExistsException.class,
      InvalidClientIdException.class,
      InvalidSortingParameterException.class,
      InvalidDateFormatException.class
  })
  public ResponseEntity<GenericError> handlerBadRequest(Exception ex) {
    GenericError error = new GenericError(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

}

package com.agencia.backend.presentation.handlers;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.agencia.backend.domain.exceptions.client.ClientAlreadyExistsException;
import com.agencia.backend.domain.exceptions.client.ClientNotFoundException;
import com.agencia.backend.domain.exceptions.client.InvalidClientIdException;
import com.agencia.backend.domain.exceptions.client.InvalidPassportDatesException;
import com.agencia.backend.domain.exceptions.client.InvalidSortingParameterException;
import com.agencia.backend.domain.exceptions.global.InvalidDateFormatException;
import com.agencia.backend.presentation.dto.error.GenericError;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

public class ClientExceptionHandlerTest {

  private final ClientExceptionHandler handler = new ClientExceptionHandler();

  @Test
  void ShouldHandleInvalidPassportDatesException() {
    // Arrange
    InvalidPassportDatesException exception =
        new InvalidPassportDatesException("Datas do passaporte estão inválidas");

    // Act
    ResponseEntity<GenericError> response = handler.handlerBadRequest(exception);

    //Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals("Datas do passaporte estão inválidas", response.getBody().message())

    );
  }

  @Test
  void ShouldHandleClientAlreadyExistsException() {
    // Arrange
    ClientAlreadyExistsException exception = new ClientAlreadyExistsException("Cliente já cadastrado");

    // Act
    ResponseEntity<GenericError> response = handler.handlerBadRequest(exception);

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals(exception.getMessage(), response.getBody().message()));
  }

  @Test
  void handleInvalidSortingParameterException(){
    // Arrange
    InvalidSortingParameterException exception = new InvalidSortingParameterException("Parâmetro de ordenação inválido");

    // Act
    ResponseEntity<GenericError> response = handler.handlerBadRequest(exception);

    // Assert
    assertAll(
        () -> assertNotNull(response),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals(exception.getMessage(), response.getBody().message())

    );
  }

  @Test
  void handleInvalidDateFormatException() {
    // Arrange
    InvalidDateFormatException exception = new InvalidDateFormatException("Formato de data inválido");

    // Act
    ResponseEntity<GenericError> response = handler.handlerBadRequest(exception);

    assertAll(
        () -> assertNotNull(response),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals(exception.getMessage(), response.getBody().message())
    );
  }

  @Test
  void handleClientNotFoundException() {
    // Arrange
    ClientNotFoundException exception = new ClientNotFoundException("Nenhum cliente encontrado com o ID: 1");

    // Act
    ResponseEntity<GenericError> response = handler.handleClientNotFoundException(exception);

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(404, response.getStatusCode().value()),
        () -> assertEquals(404, response.getBody().statusCode()),
        () -> assertEquals(exception.getMessage(), response.getBody().message())
    );
  }

  @Test
  void handlerInvalidClientIdException() {
    // Arrange
    InvalidClientIdException exception = new InvalidClientIdException(
        "O ID do cliente deve ser um valor positivo.");

    // Act
    ResponseEntity<GenericError> response = handler.handlerBadRequest(exception);

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals(exception.getMessage(), response.getBody().message())
    );
  }
}

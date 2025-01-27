package com.agencia.backend.presentation.handlers;

import com.agencia.backend.domain.exceptions.global.DatabaseException;
import com.agencia.backend.presentation.dto.error.GenericError;
import com.agencia.backend.presentation.dto.error.ValidationError;
import com.agencia.backend.presentation.dto.error.ValidationErrorsResponse;
import jakarta.validation.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

  private GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  void handleDateTimeParseException() {
    // Act
    ResponseEntity<GenericError> response = handler.handleDateTimeParseException();

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(400, response.getStatusCode().value()),
        () -> assertEquals(400, response.getBody().statusCode()),
        () -> assertEquals("Um formato inválido de data foi inserido", response.getBody().message())
    );

  }

  @Test
  void handleException() {
    // Act
    ResponseEntity<GenericError> response = handler.handleGenericException();

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(500, response.getStatusCode().value()),
        () -> assertEquals(500, response.getBody().statusCode()),
        () -> assertEquals("Ocorreu um erro inesperado. Se persistir, reinicie o sistema.", response.getBody().message())
    );
  }

  @Test
  void handleConstraintViolationException() {
    // Arrange: Simula uma ConstraintViolationException
    ConstraintViolation<?> mockViolation1 = mock(ConstraintViolation.class);
    Path mockPath1 = mock(Path.class);
    when(mockPath1.toString()).thenReturn("field1");
    when(mockViolation1.getPropertyPath()).thenReturn(mockPath1);
    when(mockViolation1.getMessage()).thenReturn("Field1 é inválido");

    ConstraintViolation<?> mockViolation2 = mock(ConstraintViolation.class);
    Path mockPath2 = mock(Path.class);
    when(mockPath2.toString()).thenReturn("field2");
    when(mockViolation2.getPropertyPath()).thenReturn(mockPath2);
    when(mockViolation2.getMessage()).thenReturn("Field2 é obrigatório");

    Set<ConstraintViolation<?>> violations = new HashSet<>();
    violations.add(mockViolation1);
    violations.add(mockViolation2);

    ConstraintViolationException exception = new ConstraintViolationException(violations);

    // Act
    ResponseEntity<Object> response = handler.handleConstraintViolationException(exception);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    @SuppressWarnings("unchecked")
    Set<ValidationError> responseBody = (Set<ValidationError>) response.getBody();
    assertEquals(2, responseBody.size());

    ValidationError error1 = responseBody.stream()
        .filter(error -> error.field().equals("field1"))
        .findFirst()
        .orElseThrow(() -> new AssertionError("Field1 não encontrado"));
    assertEquals("Field1 é inválido", error1.message());

    ValidationError error2 = responseBody.stream()
        .filter(error -> error.field().equals("field2"))
        .findFirst()
        .orElseThrow(() -> new AssertionError("Field2 não encontrado"));
    assertEquals("Field2 é obrigatório", error2.message());
  }

  @Test
  void handleValidationException(){
    // Arrange
    FieldError fieldError1 = new FieldError("objectName", "field1", "Field1 é inválido");
    FieldError fieldError2 = new FieldError("objectName", "field2", "Field2 é obrigatório");
    FieldError fieldError3 = new FieldError("objectName", "field1", "Outro erro para o field1");

    List<FieldError> fieldErrors = new ArrayList<>();
    fieldErrors.add(fieldError1);
    fieldErrors.add(fieldError2);
    fieldErrors.add(fieldError3);

    BindingResult mockBindingResult = mock(BindingResult.class);
    when(mockBindingResult.getFieldErrors()).thenReturn(fieldErrors);

    MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
    when(exception.getBindingResult()).thenReturn(mockBindingResult);

    // Act
    ResponseEntity<ValidationErrorsResponse> response = handler.handleValidationException(exception);

    // Assert
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    ValidationErrorsResponse responseBody = response.getBody();
    assertEquals("Erro de validação nos campos enviados", responseBody.message());
    assertEquals(HttpStatus.BAD_REQUEST.value(), responseBody.statusCode());

    Set<ValidationError> errors = responseBody.errors();
    assertEquals(2, errors.size());

    ValidationError error1 = errors.stream()
        .filter(error -> error.field().equals("field1"))
        .findFirst()
        .orElseThrow(() -> new AssertionError("Field1 não encontrado"));
    assertEquals("Field1 é inválido", error1.message());

    ValidationError error2 = errors.stream()
        .filter(error -> error.field().equals("field2"))
        .findFirst()
        .orElseThrow(() -> new AssertionError("Field2 não encontrado"));
    assertEquals("Field2 é obrigatório", error2.message());
  }

  @Test
  void handlerDataBaseException() {
    // Arrange
    DatabaseException exception = new DatabaseException("Erro ao realizar operações no banco de dados.");

    // Act
    ResponseEntity<GenericError> response = handler.handleDatabaseException();

    // Assert
    assertAll(
        () -> assertNotNull(response.getBody()),
        () -> assertEquals(500, response.getStatusCode().value()),
        () -> assertEquals(500, response.getBody().statusCode()),
        () -> assertEquals("Ocorreu um erro inesperado ao realizar operações no banco de dados.", response.getBody().message()));
  }
}
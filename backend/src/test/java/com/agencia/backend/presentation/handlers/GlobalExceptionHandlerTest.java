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
import java.util.Set;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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
    Exception exception = new Exception("Exceção de inesperada");

    // Act
    ResponseEntity<GenericError> response = handler.handleGenericException(exception);

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
    // Arrange
    ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
    Path path1 = mock(Path.class);
    when(path1.toString()).thenReturn("field1");
    when(violation1.getPropertyPath()).thenReturn(path1);
    when(violation1.getMessage()).thenReturn("Field1 é inválido");

    ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);
    Path path2 = mock(Path.class);
    when(path2.toString()).thenReturn("field2");
    when(violation2.getPropertyPath()).thenReturn(path2);
    when(violation2.getMessage()).thenReturn("Field2 é obrigatório");

    Set<ConstraintViolation<?>> violations = Set.of(violation1, violation2);
    ConstraintViolationException exception = new ConstraintViolationException(violations);

    // Act
    ResponseEntity<Object> response = handler.handleConstraintViolationException(exception);

    // Assert
    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    ValidationErrorsResponse responseBody = assertInstanceOf(
        ValidationErrorsResponse.class,
        response.getBody());

    Set<ValidationError> errors = responseBody.errors();
    assertNotNull(errors);
    assertEquals(2, errors.size());
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
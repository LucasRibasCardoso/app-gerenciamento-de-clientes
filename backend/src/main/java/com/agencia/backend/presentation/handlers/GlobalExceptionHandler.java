package com.agencia.backend.presentation.handlers;

import com.agencia.backend.presentation.dto.error.GenericError;
import com.agencia.backend.presentation.dto.error.ValidationError;
import com.agencia.backend.presentation.dto.error.ValidationErrorsResponse;
import com.agencia.backend.domain.exceptions.global.DatabaseException;
import jakarta.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice()
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ValidationErrorsResponse> handleValidationException(MethodArgumentNotValidException ex) {

    Set<ValidationError> errors = ex.getBindingResult().getFieldErrors().stream()
        .collect(Collectors.toMap(
            error -> error.getField(), // Uso o nome do campo como chave
            error -> new ValidationError(error.getField(), error.getDefaultMessage()),
            (existing, replacement) -> existing, // Manter o primeiro erro em caso de conflito
            LinkedHashMap::new // preservar a ordem original
        ))
        .values().stream()
        .collect(Collectors.toSet());

    ValidationErrorsResponse response = new ValidationErrorsResponse(
        "Erro de validação nos campos enviados",
        HttpStatus.BAD_REQUEST.value(),
        errors
    );
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<GenericError> handleDateTimeParseException() {
    GenericError error = new GenericError("Um formato inválido de data foi inserido", HttpStatus.BAD_REQUEST.value());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  @ExceptionHandler(DatabaseException.class)
  public ResponseEntity<GenericError> handleDatabaseException() {
    GenericError error = new GenericError("Ocorreu um erro inesperado ao realizar operações no banco de dados.", HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<GenericError> handleGenericException(Exception ex) {
    GenericError error = new GenericError("Ocorreu um erro inesperado: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex) {

    Set<ValidationError> errors = ex.getConstraintViolations().stream()
        .map(error -> new ValidationError(
            error.getPropertyPath().toString(),
            error.getMessage()
        ))
        .collect(Collectors.toSet());

    ValidationErrorsResponse response = new ValidationErrorsResponse(
        "Erro de validação nos campos enviados",
        HttpStatus.BAD_REQUEST.value(),
        errors
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
  }
}

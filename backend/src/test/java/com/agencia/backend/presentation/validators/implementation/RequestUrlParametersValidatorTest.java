package com.agencia.backend.presentation.validators.implementation;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.agencia.backend.domain.exceptions.client.InvalidClientIdException;
import com.agencia.backend.domain.exceptions.client.InvalidSortingParameterException;
import com.agencia.backend.presentation.validators.client.implementation.UrlParametersValidatorImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RequestUrlParametersValidatorTest {

  private UrlParametersValidatorImp validator;

  @BeforeEach
  void setUp() {
    validator = new UrlParametersValidatorImp();
  }

  @Test
  void ShouldThrowException_WhenIdIsNull() {
    // Arrange
    String message = "O ID deve ser um valor positivo.";

    // Act & Assert
    InvalidClientIdException exception = assertThrows(
        InvalidClientIdException.class, () -> validator.validateID(null));

    assertEquals(message, exception.getMessage());
  }

  @Test
  void ShouldThrowException_WhenIdIsNegative() {
    // Arrange
    String message = "O ID deve ser um valor positivo.";

    // Act & Assert
    InvalidClientIdException exception = assertThrows(
        InvalidClientIdException.class, () -> validator.validateID(-1L));

    assertEquals(message, exception.getMessage());
  }

  @Test
  void ShouldThrowException_WhenOrderByIsNull() {
    // Arrange
    String message = "Parâmetro de ordenação inválido. Você pode ordenar os resultados pelo nome do cliente ou pelo seu ID.";

    // Act & Assert
    InvalidSortingParameterException exception = assertThrows(
        InvalidSortingParameterException.class, () -> validator.validateOrderBy(null));

    assertEquals(message, exception.getMessage());
  }

  @Test
  void ShouldThrowException_WhenOrderByIsInvalid() {
    // Arrange
    String message = "Parâmetro de ordenação inválido. Você pode ordenar os resultados pelo nome do cliente ou pelo seu ID.";

    InvalidSortingParameterException exception = assertThrows(InvalidSortingParameterException.class,
        () -> validator.validateOrderBy("invalid"));

    assertEquals(message, exception.getMessage());
  }

  @Test
  void ShouldThrowException_WhenSortingOrderIsNull() {
    // Arrange
    String message = "Direção de ordenação inválida. Você pode ordenar os resultados em ordem crescente ou decrescente.";

    // Act & Assert
    InvalidSortingParameterException exception = assertThrows(InvalidSortingParameterException.class,
        () -> validator.validateSortOrder(null));

    assertEquals(message, exception.getMessage());
  }

  @Test
  void ShouldThrowException_WhenSortingOrderIsInvalid() {
    // Arrange
    String message = "Direção de ordenação inválida. Você pode ordenar os resultados em ordem crescente ou decrescente.";

    // Act & Assert
    InvalidSortingParameterException exception = assertThrows(InvalidSortingParameterException.class,
        () -> validator.validateSortOrder("invalid"));

    assertEquals(message, exception.getMessage());
  }
}
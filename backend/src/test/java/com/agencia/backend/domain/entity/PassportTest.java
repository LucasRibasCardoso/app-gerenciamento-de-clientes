package com.agencia.backend.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.agencia.backend.domain.exceptions.client.InvalidPassportDatesException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class PassportTest {

  private Passport createPassportDomain(LocalDate emissionDate, LocalDate expirationDate) {
    return new Passport(
        "AB123456",
        emissionDate,
        expirationDate);
  }

  @Test
  void ShouldNotThrowException_WhenDatesAreValid() {
    // Arrange & Act
    Passport passport = createPassportDomain(LocalDate.of(2020, 6, 1), LocalDate.of(2030, 6, 1));

    // Assert
    assertDoesNotThrow(passport::validateDates);
  }

  @Test
  void ShouldThrowException_WhenEmissionDateIsAfterExpirationDate() {
    // Arrange & Act & Assert
    InvalidPassportDatesException exception = assertThrows(InvalidPassportDatesException.class, () -> {
      Passport passport = createPassportDomain(LocalDate.of(2022, 6, 1), LocalDate.of(2021, 6, 1));
    });

    assertEquals("A data de emissão não pode ser posterior à data de validade.", exception.getMessage());

  }

  @Test
  void ShouldThrowException_WhenEmissionDateIsAfterCurrentDate() {
    // Arrange & Act & Assert
    InvalidPassportDatesException exception = assertThrows(InvalidPassportDatesException.class, () -> {
      Passport passport = createPassportDomain(LocalDate.of(2028, 6, 1), LocalDate.of(2040, 6, 1));
    });

    assertEquals("A data de emissão não pode ser posterior à data atual.", exception.getMessage());
  }

  @Test
  void ShouldThrowException_WhenExpirationDateIsBeforeCurrentDate() {
    // Arrange & Act & Assert
    InvalidPassportDatesException exception = assertThrows(InvalidPassportDatesException.class, () -> {
      Passport passport = createPassportDomain(LocalDate.of(2020, 6, 1), LocalDate.of(2024, 6, 1));
    });

    assertEquals("A data de validade não pode ser anterior à data atual. O passaporte está vencido.", exception.getMessage());

  }

  @Test
  void ShouldNotThrowException_WhenEmissionDateIsNull() {
    // Arrange & Act
    Passport passport = createPassportDomain(null, null);

    // Assert
    assertDoesNotThrow(passport::validateDates);
  }
}
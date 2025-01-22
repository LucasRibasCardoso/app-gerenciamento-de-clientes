package com.agencia.backend.domain.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.agencia.backend.domain.exceptions.client.InvalidBirthDateClientException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ClientTest {

  private Client createClientDomain(LocalDate mockBirthDate) {
    return new Client(
        null,
        "João Da Silva",
        "497.494.050-30",
        mockBirthDate,
        "(11) 98765-4321",
        "joao.silva@example.com",
        new Passport(
            "AB123456",
            LocalDate.of(2020, 6, 1),
            LocalDate.of(2030, 6, 1)
        ),
        new Address(
            "12345-678",
            "Brasil",
            "Sp",
            "São Paulo",
            "Jardim Primavera",
            "Rua Das Flores",
            "Apto 101",
            "123"
        )
    );
  }

  @Test
  void ShouldNotThrowException_WhenBirthDateIsValid() {
    // Arrange
    Client client = createClientDomain(LocalDate.of(2020, 6, 1));

    // Act & Assert
    assertDoesNotThrow(client::validateBirthDate);
  }

  @Test
  void ShouldThrowException_WhenBirthDateIsInTheFuture() {
    // Act & Assert
    InvalidBirthDateClientException exception =
        assertThrows(InvalidBirthDateClientException.class, () -> {
          Client client = createClientDomain(LocalDate.of(2030, 6, 1));
        });

    assertEquals("A data de nascimento deve ser anterior à data atual", exception.getMessage());
  }

  @Test
  void ShouldNotThrowException_WhenBirthDateIsNull() {
    // Arrange
    Client client = createClientDomain(null);

    // Act & Assert
    assertDoesNotThrow(client::validateBirthDate);
  }

}
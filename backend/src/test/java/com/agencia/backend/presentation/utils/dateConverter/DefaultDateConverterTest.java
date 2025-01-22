package com.agencia.backend.presentation.utils.dateConverter;

import static org.junit.jupiter.api.Assertions.*;

import com.agencia.backend.domain.exceptions.global.InvalidDateFormatException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DefaultDateConverterTest {

  private DefaultDateConverter defaultDateConverter = new DefaultDateConverter();

  @Test
  void ShouldThrowException_WhenInvalidDateFormatForConvertToLocalDate() {
    // Arrange
    String invalidDate = "01-01-2023";
    String errorMessage = "Formato inválido da data, utilize dd/MM/yyyy";

    InvalidDateFormatException exception = assertThrows(InvalidDateFormatException.class, () -> {
      defaultDateConverter.convertToLocalDate(invalidDate);
    });

    assertEquals(errorMessage, exception.getMessage());

  }

  @Test
  void ShouldParseValidDate() {
    // Arrange
    String date = "01/01/2023";

    // Act
    LocalDate parsedDate = defaultDateConverter.convertToLocalDate(date);

    // Assert
    assertAll(() -> assertInstanceOf(LocalDate.class, parsedDate), () -> assertNotNull(parsedDate),
        () -> assertEquals(2023, parsedDate.getYear()), () -> assertEquals(1, parsedDate.getMonthValue()),
        () -> assertEquals(1, parsedDate.getDayOfMonth())
    );

  }

  @Test
  void ShouldReturnNull_WhenInputIsNullForConvertToLocalDate() {
    // Act
    LocalDate parsedDate = defaultDateConverter.convertToLocalDate(null);

    // Assert
    assertNull(parsedDate);
  }

  @Test
  void ShouldConvertToString() {
    // Arrange
    LocalDate date = LocalDate.of(2023, 1, 1);

    // Act
    String formattedDate = defaultDateConverter.convertToString(date);

    // Assert
    assertAll(() -> assertNotNull(formattedDate), () -> assertEquals("01/01/2023", formattedDate));
  }

  @Test
  void ShouldReturnNull_WhenInputIsNullForConvertToString() {
    // Arrange & Act
    String formattedDate = defaultDateConverter.convertToString(null);

    // Assert
    assertNull(formattedDate);
  }

}
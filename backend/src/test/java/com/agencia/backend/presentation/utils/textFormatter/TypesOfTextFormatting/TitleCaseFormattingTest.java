package com.agencia.backend.presentation.utils.textFormatter.TypesOfTextFormatting;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TitleCaseFormattingTest {

  private TitleCaseFormatting formatter = new TitleCaseFormatting();

  @Test
  void ShouldReturnNull_WhenInputIsNull() {
    // Arrange
    String input = null;

    // Act
    String result = formatter.format(input);

    // Assert
    assertNull(result);
  }

  @Test
  void ShouldReturnEmptyString_WhenInputIsEmpty() {
    // Arrange
    String input = "";

    // Act
    String result = formatter.format(input);

    // Assert
    assertEquals("", result);
  }

  @Test
  void ShouldConvertSingleWordToTitleCase() {
    // Arrange
    String input = "hello";

    // Act
    String result = formatter.format(input);

    // Assert
    assertEquals("Hello", result);
  }

  @Test
  void ShouldConvertMultipleWordsToTitleCase() {
    // Arrange
    String input = "hello world";

    // Act
    String result = formatter.format(input);

    // Assert
    assertEquals("Hello World", result);
  }

  @Test
  void ShouldHandleWordsWithMixedCase() {
    // Arrange
    String input = "heLLo WoRLd";

    // Act
    String result = formatter.format(input);

    // Assert
    assertEquals("Hello World", result);
  }

  @Test
  void ShouldPreserveWhitespace() {
    // Arrange
    String input = " hello   world ";

    // Act
    String result = formatter.format(input);

    // Assert
    assertEquals(" Hello   World ", result);
  }

  @Test
  void ShouldHandleNonAlphabeticCharacters() {
    // Arrange
    String input = "123 hello_world!";

    // Act
    String result = formatter.format(input);

    // Assert
    assertEquals("123 Hello_world!", result);
  }

  @Test
  void ShouldHandleSingleCharacterWords() {
    // Arrange
    String input = "a b c d";

    // Act
    String result = formatter.format(input);

    // Assert
    assertEquals("A B C D", result);
  }
}
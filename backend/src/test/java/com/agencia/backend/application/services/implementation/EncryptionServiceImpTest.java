package com.agencia.backend.application.services.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.agencia.backend.infrastructure.configuration.encryption.EncryptionConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EncryptionServiceImpTest {

  private EncryptionServiceImp encryptionService;
  private final String TEST_SECRET = "testSecret123!";
  private final String TEST_KEY = "superStrongEncryptionKey";

  @BeforeEach
  void setUp() {
    EncryptionConfig config = mock(EncryptionConfig.class);
    when(config.getEncryptionKey()).thenReturn(TEST_KEY);
    when(config.getHmacSecret()).thenReturn(TEST_SECRET);
    encryptionService = new EncryptionServiceImp(config);
  }

  @Test
  void encrypt_ShouldReturnDifferentString_WhenValidInput() {
    // Arrange
    String original = "dado sensível";

    // Act
    String encrypted = encryptionService.encrypt(original);

    // Assert
    assertNotNull(encrypted);
    assertNotEquals(original, encrypted);
  }

  @Test
  void decrypt_ShouldRecoverOriginalText_WhenValidCipherText() {
    // Arrange
    String original = "informação confidencial";
    String encrypted = encryptionService.encrypt(original);

    // Act
    String decrypted = encryptionService.decrypt(encrypted);

    // Assert
    assertEquals(original, decrypted);
  }

  @Test
  void encryptDecrypt_ShouldHandleSpecialCharacters() {
    // Arrange
    String original = "Senha@123! çãõ";

    // Act
    String encrypted = encryptionService.encrypt(original);
    String decrypted = encryptionService.decrypt(encrypted);

    // Assert
    assertEquals(original, decrypted);
  }

  @Test
  void hash_ShouldProduceConsistentResults_ForSameInput() {
    // Arrange
    String input = "mesmo valor";

    // Act
    String hash1 = encryptionService.hash(input);
    String hash2 = encryptionService.hash(input);

    // Assert
    assertNotNull(hash1);
    assertEquals(hash1, hash2);
  }

  @Test
  void hash_ShouldProduceDifferentResults_ForDifferentInputs() {
    // Arrange
    String input1 = "valor1";
    String input2 = "valor2";

    // Act
    String hash1 = encryptionService.hash(input1);
    String hash2 = encryptionService.hash(input2);

    // Assert
    assertNotEquals(hash1, hash2);
  }

  @Test
  void hash_ShouldReturnNull_ForNullInput() {
    assertNull(encryptionService.hash(null));
  }

  @Test
  void hash_ShouldReturnNull_ForEmptyInput() {
    assertNull(encryptionService.hash(""));
  }

  @Test
  void encrypt_ShouldReturnNull_ForNullInput() {
    assertNull(encryptionService.encrypt(null));
  }

  @Test
  void encrypt_ShouldReturnNull_ForEmptyInput() {
    assertNull(encryptionService.encrypt(""));
  }

  @Test
  void decrypt_ShouldReturnNull_ForNullInput() {
    assertNull(encryptionService.decrypt(null));
  }

  @Test
  void decrypt_ShouldReturnNull_ForEmptyInput() {
    assertNull(encryptionService.decrypt(""));
  }

}
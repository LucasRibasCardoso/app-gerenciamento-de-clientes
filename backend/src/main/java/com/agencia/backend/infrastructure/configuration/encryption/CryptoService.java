package com.agencia.backend.infrastructure.configuration.encryption;

import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.jasypt.util.text.StrongTextEncryptor;

public class CryptoService {

  // Para criptografia (reversível)
  private static final StrongTextEncryptor encryptor = new StrongTextEncryptor();

  // Chave secreta para HMAC (armazenada em variável de ambiente)
  private static final String HMAC_SECRET = System.getenv("HMAC_KEY");

  static {
    encryptor.setPassword(System.getenv("ENCRYPTION_KEY")); // Chave de criptografia
  }

  public static String encrypt(String rawText) {
    return encryptor.encrypt(rawText);
  }

  public static String decrypt(String encryptedText) {
    return encryptor.decrypt(encryptedText);
  }

  // Hashing determinístico com HMAC
  public static String hash(String rawText) {
    try {
      SecretKeySpec key = new SecretKeySpec(HMAC_SECRET.getBytes(), "HmacSHA256");
      Mac hmac = Mac.getInstance("HmacSHA256");
      hmac.init(key);
      byte[] hashBytes = hmac.doFinal(rawText.getBytes());
      return Base64.getEncoder().encodeToString(hashBytes);
    }
    catch (Exception e) {
      throw new RuntimeException("Falha ao gerar hash", e);
    }
  }
}

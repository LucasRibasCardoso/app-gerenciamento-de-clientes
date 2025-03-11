package com.agencia.backend.application.services.implementation;

import com.agencia.backend.application.services.EncryptionService;
import com.agencia.backend.infrastructure.configuration.encryption.EncryptionConfig;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.jasypt.util.text.StrongTextEncryptor;

public class EncryptionServiceImp implements EncryptionService {

  private final StrongTextEncryptor encryptor;
  private final String hmacSecret;

  public EncryptionServiceImp(EncryptionConfig config) {
    this.encryptor = new StrongTextEncryptor();
    this.encryptor.setPassword(config.getEncryptionKey());
    this.hmacSecret = config.getHmacSecret();
  }

  @Override
  public String encrypt(String rawText) {
    if (rawText == null || rawText.isEmpty()) return null;
    return encryptor.encrypt(rawText);
  }

  @Override
  public String decrypt(String encryptedText) {
    if (encryptedText == null || encryptedText.isEmpty()) return null;
    return encryptor.decrypt(encryptedText);
  }

  @Override
  public String hash(String rawText) {
    if (rawText == null || rawText.isEmpty()) return null;

    try {
      SecretKeySpec key = new SecretKeySpec(hmacSecret.getBytes(), "HmacSHA256");
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

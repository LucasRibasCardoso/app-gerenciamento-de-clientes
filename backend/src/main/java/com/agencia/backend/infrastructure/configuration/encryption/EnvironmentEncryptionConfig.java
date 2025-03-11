package com.agencia.backend.infrastructure.configuration.encryption;

import org.springframework.beans.factory.annotation.Value;

public class EnvironmentEncryptionConfig implements EncryptionConfig {

  @Value("${encryption_key}")
  private String encryption_key;

  @Value("${hmac_key}")
  private String hmac_key;

  @Override
  public String getEncryptionKey() {
    return encryption_key;
  }

  @Override
  public String getHmacSecret() {
    return hmac_key;
  }
}

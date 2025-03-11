package com.agencia.backend.infrastructure.configuration.encryption;

public interface EncryptionConfig {
  String getEncryptionKey();
  String getHmacSecret();
}


package com.agencia.backend.infrastructure.configuration.bean.global;

import com.agencia.backend.application.services.EncryptionService;
import com.agencia.backend.application.services.implementation.EncryptionServiceImp;
import com.agencia.backend.infrastructure.configuration.encryption.EncryptionConfig;
import com.agencia.backend.infrastructure.configuration.encryption.EnvironmentEncryptionConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionBeanConfig {

  @Bean
  public EncryptionConfig encryptionConfig() {
    return new EnvironmentEncryptionConfig();
  }

  @Bean
  public EncryptionService encryptionService(EncryptionConfig config) {
    return new EncryptionServiceImp(config);
  }
}

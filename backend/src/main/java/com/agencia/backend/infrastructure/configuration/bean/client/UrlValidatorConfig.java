package com.agencia.backend.infrastructure.configuration.bean.client;

import com.agencia.backend.presentation.validators.client.UrlParametersValidator;
import com.agencia.backend.presentation.validators.client.implementation.UrlParametersValidatorImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UrlValidatorConfig {

  @Bean
  public UrlParametersValidator requestValidator() {
    return new UrlParametersValidatorImp();
  }
}

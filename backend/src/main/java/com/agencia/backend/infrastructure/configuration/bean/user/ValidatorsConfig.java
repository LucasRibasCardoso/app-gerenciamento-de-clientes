package com.agencia.backend.infrastructure.configuration.bean.user;

import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
import com.agencia.backend.presentation.validators.user.implementation.ValidateUserRequestImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorsConfig {

  @Bean
  public ValidateUserRequest validateUserRequest() {
    return new ValidateUserRequestImp();
  }
}

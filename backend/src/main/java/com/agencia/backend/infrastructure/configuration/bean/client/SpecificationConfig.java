package com.agencia.backend.infrastructure.configuration.bean.client;

import com.agencia.backend.infrastructure.model.ClientModel;
import com.agencia.backend.infrastructure.specifications.SpecificationBuilder;
import com.agencia.backend.infrastructure.specifications.implementation.ClientSpecification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpecificationConfig {

  @Bean
  public SpecificationBuilder<ClientModel> specificationBuilder() {
    return new ClientSpecification();
  }
}

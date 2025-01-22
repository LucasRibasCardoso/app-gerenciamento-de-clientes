package com.agencia.backend.infrastructure.configuration.bean.client;

import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.services.ClientExistenceValidationService;
import com.agencia.backend.application.services.ClientFieldUpdateService;
import com.agencia.backend.application.services.implementation.ClientExistenceValidationServiceImp;
import com.agencia.backend.application.services.implementation.ClientFieldUpdateServiceImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceDomain {

  @Bean
  public ClientExistenceValidationService clientExistenceValidationService(ClientRepository clientRepository) {
    return new ClientExistenceValidationServiceImp(clientRepository);
  }

  @Bean
  public ClientFieldUpdateService clientFieldUpdateService() {
    return new ClientFieldUpdateServiceImp();
  }

}

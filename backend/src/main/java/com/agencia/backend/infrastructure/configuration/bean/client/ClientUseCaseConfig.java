package com.agencia.backend.infrastructure.configuration.bean.client;

import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.services.ClientExistenceValidationService;
import com.agencia.backend.application.services.ClientFieldUpdateService;
import com.agencia.backend.application.useCase.client.CreateClientUseCase;
import com.agencia.backend.application.useCase.client.DeleteClientUseCase;
import com.agencia.backend.application.useCase.client.FindAllClientUseCase;
import com.agencia.backend.application.useCase.client.FindClientByIdUseCase;
import com.agencia.backend.application.useCase.client.UpdateClientUseCase;
import com.agencia.backend.application.useCase.client.implementation.CreateClientUseCaseImp;
import com.agencia.backend.application.useCase.client.implementation.DeleteClientUseCaseImp;
import com.agencia.backend.application.useCase.client.implementation.FindAllClientUseCaseImp;
import com.agencia.backend.application.useCase.client.implementation.FindClientByIdUseCaseImp;
import com.agencia.backend.application.useCase.client.implementation.UpdateClientUseCaseImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientUseCaseConfig {

  @Bean
  public CreateClientUseCase createClientUseCase(
      ClientRepository clientRepository,
      ClientExistenceValidationService clientExistenceValidationService) {
    return new CreateClientUseCaseImp(clientRepository, clientExistenceValidationService);
  }

  @Bean
  public FindAllClientUseCase findAllClientUseCase(ClientRepository clientRepository) {
    return new FindAllClientUseCaseImp(clientRepository);
  }

  @Bean
  public FindClientByIdUseCase findClientByIdUseCase(ClientRepository clientRepository) {
    return new FindClientByIdUseCaseImp(clientRepository);
  }

  @Bean
  public DeleteClientUseCase deleteClientUseCase(ClientRepository clientRepository) {
    return new DeleteClientUseCaseImp(clientRepository);
  }

  @Bean
  public UpdateClientUseCase updateClientUseCase(
      ClientRepository clientRepository,
      FindClientByIdUseCase findClientByIdUseCase,
      ClientFieldUpdateService clientFieldUpdateService,
      ClientExistenceValidationService clientExistenceValidationService)
  {
    return new UpdateClientUseCaseImp(
        clientRepository,
        findClientByIdUseCase,
        clientFieldUpdateService,
        clientExistenceValidationService
    );
  }


}

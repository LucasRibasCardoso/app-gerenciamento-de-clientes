package com.agencia.backend.application.useCase.client.implementation;

import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.services.ClientExistenceValidationService;
import com.agencia.backend.application.useCase.client.CreateClientUseCase;

public class CreateClientUseCaseImp implements CreateClientUseCase {

  private final ClientRepository clientRepository;
  private final ClientExistenceValidationService clientExistenceValidationService;

  public CreateClientUseCaseImp(
      ClientRepository clientRepository,
      ClientExistenceValidationService clientExistenceValidationService
  ) {
    this.clientRepository = clientRepository;
    this.clientExistenceValidationService = clientExistenceValidationService;
  }

  @Override
  public Client createClient(Client client) {
    // Verifica se o CPF, e-mail e número de passaporte já estão cadastrados
    clientExistenceValidationService.validateCpf(client);
    clientExistenceValidationService.validateEmail(client);
    clientExistenceValidationService.validatePassportNumber(client);

    return clientRepository.save(client);
  }

}
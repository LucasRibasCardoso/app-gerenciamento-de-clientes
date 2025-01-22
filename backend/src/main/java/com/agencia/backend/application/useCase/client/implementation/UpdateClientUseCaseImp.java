package com.agencia.backend.application.useCase.client.implementation;

import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.services.ClientExistenceValidationService;
import com.agencia.backend.application.services.ClientFieldUpdateService;
import com.agencia.backend.application.useCase.client.FindClientByIdUseCase;
import com.agencia.backend.application.useCase.client.UpdateClientUseCase;

public class UpdateClientUseCaseImp implements UpdateClientUseCase {

  private final ClientRepository clientRepository;
  private final FindClientByIdUseCase findClientByIdUseCase;
  private final ClientFieldUpdateService clientFieldUpdateService;
  private final ClientExistenceValidationService clientExistenceValidationService;

  public UpdateClientUseCaseImp(
      ClientRepository clientRepository,
      FindClientByIdUseCase findClientByIdUseCase,
      ClientFieldUpdateService clientFieldUpdateService,
      ClientExistenceValidationService clientExistenceValidationService
  ) {
    this.clientRepository = clientRepository;
    this.findClientByIdUseCase = findClientByIdUseCase;
    this.clientFieldUpdateService = clientFieldUpdateService;
    this.clientExistenceValidationService = clientExistenceValidationService;
  }

  @Override
  public Client update(Long id, Client clientRequest) {
    // Verifica se o já cliente existe
    Client existingClient = findClientByIdUseCase.getClient(id);

    // Verifica se o e-mail e o número de passaporte já estão cadastrados
    clientExistenceValidationService.validateEmail(clientRequest);
    clientExistenceValidationService.validatePassportNumber(clientRequest);

    // Atualiza o cliente com os novos valores
    Client clientUpdated = clientFieldUpdateService.updateClient(existingClient, clientRequest);

    // Salva o cliente atualizado no banco de dados
    return clientRepository.save(clientUpdated);
  }



}

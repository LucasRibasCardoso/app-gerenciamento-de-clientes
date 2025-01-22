package com.agencia.backend.application.useCase.client.implementation;

import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.exceptions.client.ClientNotFoundException;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.useCase.client.FindClientByIdUseCase;

public class FindClientByIdUseCaseImp implements FindClientByIdUseCase {

  private final ClientRepository clientRepository;

  public FindClientByIdUseCaseImp(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Client getClient(Long id) {
    return clientRepository.findById(id).orElseThrow(
        () -> new ClientNotFoundException("Nenhum cliente encontrado com o ID: " + id)
    );
  }

}

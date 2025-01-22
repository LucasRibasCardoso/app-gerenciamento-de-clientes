package com.agencia.backend.application.useCase.client.implementation;

import com.agencia.backend.domain.exceptions.client.ClientNotFoundException;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.useCase.client.DeleteClientUseCase;

public class DeleteClientUseCaseImp implements DeleteClientUseCase {

  private final ClientRepository clientRepository;

  public DeleteClientUseCaseImp(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public void deleteClient(Long id) {
    if (!clientRepository.existsById(id)) {
      throw new ClientNotFoundException("Nenhum cliente encontrado com o ID: " + id);
    }

    clientRepository.deleteById(id);

  }

}

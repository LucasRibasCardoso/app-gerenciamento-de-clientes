package com.agencia.backend.application.useCase.client.implementation;

import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.useCase.client.FindAllClientUseCase;
import java.util.List;

public class FindAllClientUseCaseImp implements FindAllClientUseCase {

  private final ClientRepository clientRepository;

  public FindAllClientUseCaseImp(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public List<Client> getClients(String search, String orderBy, String sortOrder, int page, int size) {
    return clientRepository.findAll(search, orderBy, sortOrder, page, size);
  }

}

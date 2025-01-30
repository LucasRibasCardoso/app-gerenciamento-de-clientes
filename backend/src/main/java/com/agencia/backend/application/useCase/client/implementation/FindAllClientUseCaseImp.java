package com.agencia.backend.application.useCase.client.implementation;

import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.useCase.client.FindAllClientUseCase;
import org.springframework.data.domain.Page;

public class FindAllClientUseCaseImp implements FindAllClientUseCase {

  private final ClientRepository clientRepository;

  public FindAllClientUseCaseImp(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Page<Client> getClients(String search, String orderBy, String sortOrder, int page, int size) {
    return clientRepository.findAll(search, orderBy, sortOrder, page, size);
  }

}

package com.agencia.backend.application.useCase.client;

import com.agencia.backend.domain.entity.Client;

public interface UpdateClientUseCase {
  Client update(Long id, Client client);
}

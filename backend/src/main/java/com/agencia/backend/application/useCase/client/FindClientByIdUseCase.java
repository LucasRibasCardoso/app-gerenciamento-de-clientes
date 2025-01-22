package com.agencia.backend.application.useCase.client;

import com.agencia.backend.domain.entity.Client;

public interface FindClientByIdUseCase {
  Client getClient(Long id);
}

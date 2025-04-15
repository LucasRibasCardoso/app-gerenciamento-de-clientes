package com.agencia.backend.application.services;

import com.agencia.backend.domain.entity.Client;

public interface ClientFieldUpdateService {
  Client updateClient(Client existingClient, Client clientRequest);
}

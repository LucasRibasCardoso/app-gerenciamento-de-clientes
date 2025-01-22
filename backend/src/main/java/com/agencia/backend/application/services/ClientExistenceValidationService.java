package com.agencia.backend.application.services;

import com.agencia.backend.domain.entity.Client;

public interface ClientExistenceValidationService {
  void validateCpf(Client clientRequest);
  void validatePassportNumber(Client clientRequest);
  void validateEmail(Client clientRequest);
}

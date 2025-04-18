package com.agencia.backend.application.services.implementation;

import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.exceptions.client.ClientAlreadyExistsException;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.services.ClientExistenceValidationService;
import com.agencia.backend.domain.repository.PassportRepository;

public class ClientExistenceValidationServiceImp implements ClientExistenceValidationService {
  private final ClientRepository clientRepository;
  private final PassportRepository passportRepository;

  public ClientExistenceValidationServiceImp(ClientRepository clientRepository, PassportRepository passportRepository) {
    this.clientRepository = clientRepository;
    this.passportRepository = passportRepository;
  }

  @Override
  public void validateCpf(Client clientRequest) {
    if (clientRequest.getCpf() != null && clientRepository.existsByCpf(clientRequest.getCpf())) {
      throw new ClientAlreadyExistsException("Esse CPF já está em uso.");
    }
  }

  @Override
  public void validatePassportNumber(Client clientRequest) {
    if (clientRequest.getPassport() != null
        && clientRequest.getPassport().getNumber() != null
        && passportRepository.existsByPassportNumber(clientRequest.getPassport().getNumber())) {
      throw new ClientAlreadyExistsException("Esse número de passaporte já está em uso.");
    }
  }

  @Override
  public void validateEmail(Client clientRequest) {
    if (clientRequest.getEmail() != null && clientRepository.existsByEmail(clientRequest.getEmail())) {
      throw new ClientAlreadyExistsException("Esse email já está em uso.");
    }
  }
}

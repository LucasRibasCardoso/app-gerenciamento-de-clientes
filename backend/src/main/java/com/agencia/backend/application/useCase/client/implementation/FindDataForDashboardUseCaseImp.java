package com.agencia.backend.application.useCase.client.implementation;

import com.agencia.backend.application.useCase.client.FindDataForDashboardUseCase;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.repository.ClientRepository;
import java.util.List;

public class FindDataForDashboardUseCaseImp implements FindDataForDashboardUseCase {

  private final ClientRepository clientRepository;

  public FindDataForDashboardUseCaseImp(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Override
  public Long getTotalClients() {
    return clientRepository.getTotalClients();
  }

  @Override
  public Long getTotalNewClientsLast30Days() {
    return clientRepository.getTotalNewClientsLast30Days();
  }

  @Override
  public Long getTotalClientsWithPassport() {
    return clientRepository.getTotalClientsWithPassport();
  }

  @Override
  public Long getTotalClientsWithoutPassport() {
    return clientRepository.getTotalClientsWithoutPassport();
  }

  @Override
  public List<Client> getClientsThatNeedToRenewPassport() {
    return clientRepository.getClientsThatNeedToRenewPassport();
  }

}

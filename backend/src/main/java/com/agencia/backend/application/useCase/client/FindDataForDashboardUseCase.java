package com.agencia.backend.application.useCase.client;

import com.agencia.backend.domain.entity.Client;

import java.util.List;

public interface FindDataForDashboardUseCase {
    // Dashboard
    Long getTotalClients();

    Long getTotalNewClientsLast30Days();

    Long getTotalClientsWithPassport();

    Long getTotalClientsWithoutPassport();

    List<Client> getClientsThatNeedToRenewPassport();
}

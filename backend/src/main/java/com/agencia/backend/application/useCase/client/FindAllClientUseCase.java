package com.agencia.backend.application.useCase.client;

import com.agencia.backend.domain.entity.Client;
import java.util.List;

public interface FindAllClientUseCase {
  List<Client> getClients(String search, String orderBy, String sortOrder, int page, int size);
}

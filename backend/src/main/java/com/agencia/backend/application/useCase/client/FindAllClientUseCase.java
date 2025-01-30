package com.agencia.backend.application.useCase.client;

import com.agencia.backend.domain.entity.Client;
import java.util.List;
import org.springframework.data.domain.Page;

public interface FindAllClientUseCase {
  Page<Client> getClients(String search, String orderBy, String sortOrder, int page, int size);
}

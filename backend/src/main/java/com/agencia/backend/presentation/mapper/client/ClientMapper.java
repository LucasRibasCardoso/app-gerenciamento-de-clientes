package com.agencia.backend.presentation.mapper.client;

import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.infrastructure.model.ClientModel;
import com.agencia.backend.presentation.dto.client.ClientRequestDTO;
import com.agencia.backend.presentation.dto.client.ClientRequestUpdateDTO;
import com.agencia.backend.presentation.dto.client.ClientResponseDTO;

public interface ClientMapper {
  Client toDomain(ClientRequestDTO dto);
  Client toDomain(ClientRequestUpdateDTO dto);
  Client toDomain(ClientModel model);
  ClientModel toModel(Client domain);
  ClientResponseDTO toDTO(Client domain);
}

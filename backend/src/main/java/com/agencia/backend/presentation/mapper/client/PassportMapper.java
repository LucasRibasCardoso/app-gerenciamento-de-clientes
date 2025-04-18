package com.agencia.backend.presentation.mapper.client;

import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.infrastructure.model.PassportModel;
import com.agencia.backend.presentation.dto.passport.PassportDTO;

public interface PassportMapper {
  Passport toDomain(PassportDTO dto);
  PassportDTO toDTO(Passport domain);
  PassportModel toModel(Passport domain);
  Passport toDomain(PassportModel model);
}

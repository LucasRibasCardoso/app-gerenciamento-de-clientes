package com.agencia.backend.presentation.mapper.client;

import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.presentation.dto.passport.PassportDTO;

public interface PassportMapper {
  Passport toDomain(PassportDTO dto);
  PassportDTO toDTO(Passport domain);
}

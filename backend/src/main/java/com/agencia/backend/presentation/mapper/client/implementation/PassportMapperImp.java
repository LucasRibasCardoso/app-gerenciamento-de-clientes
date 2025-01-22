package com.agencia.backend.presentation.mapper.client.implementation;

import com.agencia.backend.presentation.dto.passport.PassportDTO;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.presentation.mapper.client.PassportMapper;
import com.agencia.backend.presentation.utils.dateConverter.DateConverter;

public class PassportMapperImp implements PassportMapper {

  private final DateConverter dateConverter;

  public PassportMapperImp(DateConverter dateConverter) {
    this.dateConverter = dateConverter;
  }

  @Override
  public Passport toDomain(PassportDTO dto) {
    if (dto == null) {
      return new Passport(null, null, null);
    }

    return new Passport(
        dto.number(),
        dateConverter.convertToLocalDate(dto.emissionDate()),
        dateConverter.convertToLocalDate(dto.expirationDate())
    );
  }

  @Override
  public PassportDTO toDTO(Passport domain) {
    return new PassportDTO(
        domain.getNumber(),
        dateConverter.convertToString(domain.getEmissionDate()),
        dateConverter.convertToString(domain.getExpirationDate())
    );
  }



}

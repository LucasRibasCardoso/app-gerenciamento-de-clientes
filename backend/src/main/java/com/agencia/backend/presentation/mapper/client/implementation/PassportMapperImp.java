package com.agencia.backend.presentation.mapper.client.implementation;

import com.agencia.backend.infrastructure.model.PassportModel;
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
      return null;
    }

    // Verifica se todos os campos importantes são nulos
    boolean allFieldsEmpty = (dto.number() == null || dto.number().trim().isEmpty()) &&
                            dto.emissionDate() == null &&
                            dto.expirationDate() == null;

    if (allFieldsEmpty) {
      return null;
    }

    return new Passport(
        dto.number(),
        dateConverter.convertToLocalDate(dto.emissionDate()),
        dateConverter.convertToLocalDate(dto.expirationDate())
    );
  }

  @Override
  public PassportDTO toDTO(Passport domain) {
    if (domain == null) {
      return null;
    }

    return new PassportDTO(
        domain.getNumber(),
        dateConverter.convertToString(domain.getEmissionDate()),
        dateConverter.convertToString(domain.getExpirationDate())
    );
  }

  @Override
  public PassportModel toModel(Passport domain) {
    if (domain == null) {
      return null;
    }

    PassportModel model = new PassportModel(
        domain.getId(),
        domain.getNumber(),
        domain.getEmissionDate(),
        domain.getExpirationDate()
    );
    return model;
  }

  @Override
  public Passport toDomain(PassportModel model) {
    if (model == null) {
      return null;
    }

    return new Passport(
        model.getId(),
        model.getPassportNumber(),
        model.getEmissionDate(),
        model.getExpirationDate()
    );
  }

}

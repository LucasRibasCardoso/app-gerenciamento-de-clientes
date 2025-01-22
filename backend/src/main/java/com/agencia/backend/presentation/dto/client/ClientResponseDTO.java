package com.agencia.backend.presentation.dto.client;

import com.agencia.backend.presentation.dto.address.AddressDTO;
import com.agencia.backend.presentation.dto.passport.PassportDTO;

public record ClientResponseDTO(
    Long id,
    String completeName,
    String cpf,
    String birthDate,
    String phone,
    String email,
    PassportDTO passport,
    AddressDTO address) {}

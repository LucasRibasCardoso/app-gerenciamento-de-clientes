package com.agencia.backend.presentation.dto.passport;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record PassportDTO (

    @Pattern(
        regexp = "^[A-Z0-9]{6,9}$",
        message = "O número do passaporte deve conter entre 6 e 9 caracteres alfanuméricos")
    @Size(max = 9, message = "O número do passaporte deve ter no máximo 9 caracteres")
    String number,

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Pattern(
        regexp = "^\\d{2}/\\d{2}/\\d{4}$",
        message = "Data de emissão deve estar no formato dd/MM/yyyy")
    String emissionDate,

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Pattern(
        regexp = "^\\d{2}/\\d{2}/\\d{4}$",
        message = "Data de vencimento deve estar no formato dd/MM/yyyy")
    String expirationDate
) {}
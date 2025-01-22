package com.agencia.backend.presentation.dto.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.agencia.backend.presentation.dto.address.AddressDTO;
import com.agencia.backend.presentation.dto.passport.PassportDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClientRequestUpdateDTO(

    @Size(max = 100, message = "O nome completo deve ter no máximo 100 caracteres")
    String name,

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Data de nascimento deve estar no formato dd/MM/yyyy")
    String birthDate,

    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "O telefone deve estar no formato (XX) XXXXX-XXXX")
    String phone,

    @Email(message = "E-mail inválido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    String email,

    PassportDTO passport,

    AddressDTO address

) {}

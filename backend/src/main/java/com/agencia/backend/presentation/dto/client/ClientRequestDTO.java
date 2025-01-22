package com.agencia.backend.presentation.dto.client;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.agencia.backend.presentation.dto.address.AddressDTO;
import com.agencia.backend.presentation.dto.passport.PassportDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClientRequestDTO(

    @NotBlank(message = "O nome completo é obrigatório")
    @Size(max = 100, message = "O nome completo deve ter no máximo 100 caracteres")
    String completeName,

    @CPF(message = "CPF inválido")
    @NotBlank(message = "O CPF é obrigatório")
    @Size(max = 14, message = "O CPF deve ter no máximo 14 caracteres")
    String cpf,

    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "A data de nascimento é obrigatória")
    @Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$", message = "Data de nascimento deve estar no formato dd/MM/yyyy")
    String birthDate,

    @Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", message = "O telefone deve estar no formato (XX) XXXXX-XXXX")
    @Size(max = 15, message = "O telefone deve ter no máximo 15 caracteres")
    String phone,

    @Email(message = "E-mail inválido")
    @Size(max = 100, message = "O e-mail deve ter no máximo 100 caracteres")
    String email,

  PassportDTO passport,

  AddressDTO address){}

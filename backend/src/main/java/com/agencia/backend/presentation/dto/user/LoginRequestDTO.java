package com.agencia.backend.presentation.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(

    @NotBlank(message = "O username precisa ser informado")
    @Size(max = 100, min = 6, message = "O nome de usuário deve ter entre 6 e 100 caracteres")
    String username,

    @NotBlank(message = "A senha precisa ser informada")
    @Size(max = 20, min = 8, message = "A senha deve ter entre 8 e 20 caracteres")
    String password
) {}
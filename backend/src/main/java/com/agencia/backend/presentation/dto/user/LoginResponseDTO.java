package com.agencia.backend.presentation.dto.user;

public record LoginResponseDTO(
        String username,
        String role,
        String token
) {
}

package com.agencia.backend.presentation.dto.user;

import java.util.List;

public record LoginResponseDTO(
    String username,
    List<String> roles,
    String token
) {}

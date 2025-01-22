package com.agencia.backend.presentation.dto.user;

import com.agencia.backend.domain.entity.enuns.Role;
import java.util.Set;
import java.util.UUID;

public record UserResponseDTO(UUID id, String username, Set<Role> roles){}

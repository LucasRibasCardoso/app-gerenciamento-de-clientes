package com.agencia.backend.application.useCase.user;

import com.agencia.backend.domain.entity.User;
import java.util.UUID;

public interface UpdateUserUseCase {
   User update(UUID id, User user);
}

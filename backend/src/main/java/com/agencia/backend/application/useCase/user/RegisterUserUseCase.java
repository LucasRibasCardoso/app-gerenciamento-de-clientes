package com.agencia.backend.application.useCase.user;

import com.agencia.backend.domain.entity.User;

public interface RegisterUserUseCase {
  User register(User user);
}

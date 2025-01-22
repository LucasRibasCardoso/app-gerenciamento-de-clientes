package com.agencia.backend.application.useCase.user;

import com.agencia.backend.domain.entity.User;

public interface FindUserByUsernameUseCase {
  User getUser(String username);
}

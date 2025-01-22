package com.agencia.backend.application.useCase.user;


import java.util.UUID;

public interface DeleteUserUseCase {
  void deleteUser(UUID id);
}

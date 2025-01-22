package com.agencia.backend.application.useCase.user.implementation;

import com.agencia.backend.domain.exceptions.user.UserNotFoundException;
import com.agencia.backend.domain.repository.UserRepository;
import com.agencia.backend.application.useCase.user.DeleteUserUseCase;
import java.util.UUID;

public class DeleteUserUseCaseImp implements DeleteUserUseCase {

  private final UserRepository userRepository;

  public DeleteUserUseCaseImp(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void deleteUser(UUID id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException("Nenhum usuário encontrado.");
    }

    userRepository.deleteById(id);
  }

}

package com.agencia.backend.application.useCase.user.implementation;

import com.agencia.backend.application.useCase.user.FindUserByIdUseCase;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.exceptions.user.UserNotFoundException;
import com.agencia.backend.domain.repository.UserRepository;
import java.util.UUID;

public class FindUserByIdUseCaseImp implements FindUserByIdUseCase {

  private final UserRepository userRepository;

  public FindUserByIdUseCaseImp(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUser(UUID id) {
    return userRepository.findUserById(id)
        .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado com o ID: " + id));
  }


}

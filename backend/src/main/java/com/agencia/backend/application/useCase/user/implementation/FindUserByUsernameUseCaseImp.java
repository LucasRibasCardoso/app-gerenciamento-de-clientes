package com.agencia.backend.application.useCase.user.implementation;

import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.exceptions.user.UserNotFoundException;
import com.agencia.backend.domain.repository.UserRepository;
import com.agencia.backend.application.useCase.user.FindUserByUsernameUseCase;

public class FindUserByUsernameUseCaseImp implements FindUserByUsernameUseCase {

  private final UserRepository userRepository;

  public FindUserByUsernameUseCaseImp(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User getUser(String username) {
    return userRepository.findByUsername(username).orElseThrow(
        () -> new UserNotFoundException("Esse usuário não esta cadastrado no sistema.")
    );
  }

}

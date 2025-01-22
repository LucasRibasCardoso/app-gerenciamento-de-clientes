package com.agencia.backend.application.useCase.user.implementation;

import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.repository.UserRepository;
import com.agencia.backend.application.useCase.user.FindAllUserUseCase;
import java.util.List;

public class FindAllUserUseCaseImp implements FindAllUserUseCase {

  private final UserRepository userRepository;

  public FindAllUserUseCaseImp(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}

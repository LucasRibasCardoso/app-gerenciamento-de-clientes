package com.agencia.backend.application.services.implementation;

import com.agencia.backend.application.services.UserExistenceValidationService;
import com.agencia.backend.domain.exceptions.user.UserAlreadyExistsException;
import com.agencia.backend.domain.repository.UserRepository;

public class UserExistenceValidationServiceImp implements UserExistenceValidationService {

  private final UserRepository userRepository;

  public UserExistenceValidationServiceImp(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void validateUsername(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new UserAlreadyExistsException("Username já está em uso! Tente outro.");
    }
  }

}

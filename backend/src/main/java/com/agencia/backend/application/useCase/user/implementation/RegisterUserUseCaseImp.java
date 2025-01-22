package com.agencia.backend.application.useCase.user.implementation;

import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.repository.UserRepository;
import com.agencia.backend.application.useCase.user.RegisterUserUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;

public class RegisterUserUseCaseImp implements RegisterUserUseCase {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public RegisterUserUseCaseImp(
      PasswordEncoder passwordEncoder,
      UserRepository userRepository
  ) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public User register(User user) {
    String encodedPassword = passwordEncoder.encode(user.getPassword());

    User userWithEncodedPassword = new User(null, user.getUsername(), encodedPassword, user.getRoles());
    return userRepository.save(userWithEncodedPassword);
  }

}

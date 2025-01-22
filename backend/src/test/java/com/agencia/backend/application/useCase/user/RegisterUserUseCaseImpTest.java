package com.agencia.backend.application.useCase.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.application.useCase.user.implementation.RegisterUserUseCaseImp;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.domain.repository.UserRepository;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class RegisterUserUseCaseImpTest {

  @InjectMocks
  private RegisterUserUseCaseImp registerUserUseCase;

  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  private User creteUserDomain() {
    return new User(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"),
        "userCommon",
        "12345678@",
        Set.of(Role.USER)
    );
  }

  @Test
  void ShouldReturnUser_WhenRegisterUser() {
    // Arrange
    User userDomain = creteUserDomain();
    String encodedPassword = "encodedPassword";

    when(passwordEncoder.encode(userDomain.getPassword())).thenReturn(encodedPassword);
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    User savedUser = registerUserUseCase.register(userDomain);

    // Assert
    assertAll(
        () -> assertEquals(userDomain.getUsername(), savedUser.getUsername()),
        () -> assertEquals(encodedPassword, savedUser.getPassword()),
        () -> assertEquals(userDomain.getRoles(), savedUser.getRoles())
    );
    verify(passwordEncoder).encode(userDomain.getPassword());
    verify(userRepository).save(any(User.class));
  }

}
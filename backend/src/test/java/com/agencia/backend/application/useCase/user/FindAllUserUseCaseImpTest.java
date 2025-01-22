package com.agencia.backend.application.useCase.user;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.agencia.backend.application.useCase.user.implementation.FindAllUserUseCaseImp;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.domain.repository.UserRepository;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindAllUserUseCaseImpTest {

  @InjectMocks
  private FindAllUserUseCaseImp findAllUserUseCase;

  @Mock
  private UserRepository userRepository;

  private User creteUserDomain() {
    return new User(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"),
        "userCommon",
        "12345678@",
        Set.of(Role.USER)
    );
  }

  @Test
  void ShouldReturnListOfUsers_WhenUsersExist() {
    // Arrange
    User userDomain = creteUserDomain();

    when(userRepository.findAll()).thenReturn(List.of(userDomain));

    // Act
    List<User> users = findAllUserUseCase.getAllUsers();

    // Assert
    assertAll(
        () -> assertEquals(1, users.size()),
        () -> assertInstanceOf(User.class, users.get(0))
    );
  }

  @Test
  void ShouldReturnEmptyList_WhenNoUsersExist() {
    // Arrange
    when(userRepository.findAll()).thenReturn(List.of());

    // Act
    List<User> users = findAllUserUseCase.getAllUsers();

    // Assert
    assertTrue(users.isEmpty());
  }
}
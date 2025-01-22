package com.agencia.backend.application.useCase.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.application.useCase.user.implementation.FindUserByUsernameUseCaseImp;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.domain.exceptions.user.UserNotFoundException;
import com.agencia.backend.domain.repository.UserRepository;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindUserByUsernameUseCaseImpTest {

  @InjectMocks
  private FindUserByUsernameUseCaseImp findUserByUsernameUseCaseImp;

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
  void ShouldReturnUser_WhenUserExists() {
    User userDomain = creteUserDomain();

    when(userRepository.findByUsername(userDomain.getUsername())).thenReturn(Optional.of(userDomain));

    // Act
    User user = findUserByUsernameUseCaseImp.getUser(userDomain.getUsername());

    // Assert
    assertAll(
        () -> assertNotNull(user),
        () -> assertEquals(userDomain.getUsername(), user.getUsername()),
        () -> assertEquals(userDomain.getId(), user.getId()),
        () -> assertEquals(userDomain.getPassword(), user.getPassword()),
        () -> assertEquals(userDomain.getRoles(), user.getRoles())
    );

    verify(userRepository).findByUsername(userDomain.getUsername());
  }

  @Test
  void ShouldThrowException_WhenUserDoesNotExists() {
    String username = "UsernameExample";

    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    // Act & Assert
    UserNotFoundException exception = assertThrows(
        UserNotFoundException.class, () -> findUserByUsernameUseCaseImp.getUser(username));

    // Assert
    assertEquals("Nenhum usuário encontrado com o nome de usuário: " + username, exception.getMessage());

    verify(userRepository).findByUsername(username);
  }
}
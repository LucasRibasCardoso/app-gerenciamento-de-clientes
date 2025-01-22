package com.agencia.backend.application.useCase.user;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.application.useCase.user.implementation.DeleteUserUseCaseImp;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.domain.exceptions.user.UserNotFoundException;
import com.agencia.backend.domain.repository.UserRepository;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteUserUseCaseImpTest {

  @InjectMocks
  private DeleteUserUseCaseImp deleteUserUseCaseImp;

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
  void ShouldDeleteUser_WhenUserExists() {
    // Arrange
    User userDomain = creteUserDomain();
    UUID userId = userDomain.getId();

    when(userRepository.existsById(userId)).thenReturn(true);
    doNothing().when(userRepository).deleteById(userId);

    // Act & Assert
    assertDoesNotThrow(() -> deleteUserUseCaseImp.deleteUser(userId));

    verify(userRepository).existsById(userId);
    verify(userRepository).deleteById(userId);
  }

  @Test
  void ShouldThrowException_WhenUserDoesNotExist() {
    // Arrange
    UUID userId = UUID.fromString("b9e16aaf-1ccf-4c2c-8aee-7ccf8eb80c97");

    when(userRepository.existsById(userId)).thenReturn(false);

    // Act & Assert
    UserNotFoundException exception = assertThrows(
        UserNotFoundException.class, () -> deleteUserUseCaseImp.deleteUser(userId));

    assertEquals("Nenhum usuário encontrado.", exception.getMessage());

    verify(userRepository).existsById(userId);
    verify(userRepository, never()).deleteById(userId);
  }
}
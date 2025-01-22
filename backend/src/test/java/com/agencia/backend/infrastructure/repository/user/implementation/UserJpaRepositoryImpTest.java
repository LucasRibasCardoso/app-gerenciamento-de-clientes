package com.agencia.backend.infrastructure.repository.user.implementation;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.infrastructure.model.UserModel;
import com.agencia.backend.infrastructure.repository.user.UserJpaRepository;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserJpaRepositoryImpTest {

  @InjectMocks
  private UserJpaRepositoryImp userJpaRepositoryImp;

  @Mock
  private UserJpaRepository userJpaRepository;

  @Mock
  private UserMapper userMapper;

  private User creteUserDomain() {
    return new User(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"),
        "userCommon",
        "12345678@",
        Set.of(Role.USER)
    );
  }
  private UserModel createUserModel() {
    return new UserModel(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"),
        "userCommon",
        "12345678@",
        Set.of(Role.USER)
    );
  }

  @Test
  void ShouldReturnOptionalUser_WhenFindByUsername() {
    // Arrange
    User user = creteUserDomain();
    UserModel userModel = createUserModel();

    when(userJpaRepository.findByUserName(user.getUsername())).thenReturn(Optional.of(userModel));
    when(userMapper.toDomain(userModel)).thenReturn(user);

    // Act
    Optional<User> result = userJpaRepositoryImp.findByUsername(user.getUsername());

    // Assert
    assertAll(
            () -> assertEquals(user.getId(), result.get().getId()),
            () -> assertEquals(user.getUsername(), result.get().getUsername()),
            () -> assertEquals(user.getPassword(), result.get().getPassword()),
            () -> assertEquals(user.getRoles(), result.get().getRoles())
    );
    verify(userJpaRepository).findByUserName(user.getUsername());
    verify(userMapper).toDomain(userModel);
  }

  @Test
  void ShouldReturnEmptyOptional_WhenFindByUsername() {
    // Arrange
    String username = "userCommon";

    when(userJpaRepository.findByUserName(username)).thenReturn(Optional.empty());

    // Act
    Optional<User> result = userJpaRepositoryImp.findByUsername(username);

    // Assert
    assertTrue(result.isEmpty());
  }

  @Test
  void ShouldDeleteUserById() {
    // Arrange
    UUID userId = UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335");
    doNothing().when(userJpaRepository).deleteById(userId);

    // Act
    userJpaRepositoryImp.deleteById(userId);

    // Assert
    verify(userJpaRepository).deleteById(userId);
  }

  @Test
  void ShouldReturnTrue_WhenExistsById() {
    // Arrange
    UUID userId = UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335");
    when(userJpaRepository.existsById(userId)).thenReturn(true);

    // Act
    boolean result = userJpaRepositoryImp.existsById(userId);

    assertTrue(result);
    verify(userJpaRepository).existsById(userId);
  }

  @Test
  void ShouldReturnFalse_WhenNotExistsById() {
    // Arrange
    UUID userId = UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335");
    when(userJpaRepository.existsById(userId)).thenReturn(false);

    // Act
    boolean result = userJpaRepositoryImp.existsById(userId);

    // Assert
    assertFalse(result);
    verify(userJpaRepository).existsById(userId);
  }

  @Test
  void ShouldReturnListOfUsers_WhenUsersExist() {
    // Arrange
    User user1 = creteUserDomain();
    User user2 = creteUserDomain();
    UserModel userModel1 = createUserModel();
    UserModel userModel2 = createUserModel();

    when(userJpaRepository.findAll()).thenReturn(List.of(userModel1, userModel2));
    when(userMapper.toDomain(userModel1)).thenReturn(user1);
    when(userMapper.toDomain(userModel2)).thenReturn(user2);

    // Act
    List<User> result = userJpaRepositoryImp.findAll();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    verify(userJpaRepository).findAll();
  }

  @Test
  void ShouldReturnEmptyList_WhenUsersDoNotExist() {
    // Arrange
    when(userJpaRepository.findAll()).thenReturn(List.of());

    // Act
    List<User> result = userJpaRepositoryImp.findAll();

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void ShouldReturnTrue_WhenUserExistsByUsername() {
    // Arrange
    User user = creteUserDomain();
    UserModel userModel = createUserModel();

    when(userJpaRepository.existsByUserName(user.getUsername())).thenReturn(true);

    // Act
    boolean result = userJpaRepositoryImp.existsByUsername(user.getUsername());

    // Assert
    assertTrue(result);
  }

  @Test
  void ShouldReturnFalse_WhenUserDoesNotExistByUsername() {
    // Arrange
    String username = "userCommon";
    when(userJpaRepository.existsByUserName(username)).thenReturn(false);

    // Act
    boolean result = userJpaRepositoryImp.existsByUsername(username);

    // Assert
    assertFalse(result);
  }

  @Test
  void ShouldReturnSavedUser_WhenSaveUser() {
    // Arrange
    User user = creteUserDomain();
    UserModel userModel = createUserModel();

    when(userMapper.toModel(user)).thenReturn(userModel);
    when(userJpaRepository.save(userModel)).thenReturn(userModel);
    when(userMapper.toDomain(userModel)).thenReturn(user);

    // Act
    User result = userJpaRepositoryImp.save(user);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(user.getId(), result.getId()),
        () -> assertEquals(user.getUsername(), result.getUsername()),
        () -> assertEquals(user.getPassword(), result.getPassword()),
        () -> assertEquals(user.getRoles(), result.getRoles())
    );
    verify(userMapper).toModel(user);
    verify(userJpaRepository).save(userModel);
    verify(userMapper).toDomain(userModel);
  }

}
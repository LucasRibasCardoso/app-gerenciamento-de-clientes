package com.agencia.backend.presentation.mapper.user;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.infrastructure.model.UserDetailsImp;
import com.agencia.backend.infrastructure.model.UserModel;
import com.agencia.backend.presentation.dto.user.UserRequestDTO;
import com.agencia.backend.presentation.dto.user.UserResponseDTO;
import com.agencia.backend.presentation.mapper.user.implementation.UserMapperImp;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserMapperTest {

  private UserMapperImp userMapper = new UserMapperImp();

  @Test
  void ShouldMapUserModelToUserDomain() {
    // Arrange
    UserModel userModel = new UserModel(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"), "userCommon", "password",Set.of(Role.USER));

    // Act
    User userResponse = userMapper.toDomain(userModel);

    // Assert
    assertAll(
        () -> assertNotNull(userResponse),
        () -> assertEquals(userModel.getId(), userResponse.getId()),
        () -> assertEquals(userModel.getUsername(), userResponse.getUsername()),
        () -> assertEquals(userModel.getPassword(), userResponse.getPassword()),
        () -> assertEquals(userModel.getRoles(), userResponse.getRoles())
    );
  }

  @Test
  void ShouldMapUserDomainToUserDetails() {
    // Arrange
    User userDomain = new User(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"), "userCommon", "password", Set.of(Role.USER));

    Set<GrantedAuthority> authoritiesExpected = new HashSet<>();
    authoritiesExpected.add(new SimpleGrantedAuthority("ROLE_USER"));

    // Act
    UserDetailsImp userResponse = userMapper.toUserDetails(userDomain);

    // Assert
    assertAll(
        () -> assertNotNull(userResponse),
        () -> assertEquals(userDomain.getId(), userResponse.getId()),
        () -> assertEquals(userDomain.getUsername(), userResponse.getUsername()),
        () -> assertEquals(userDomain.getPassword(), userResponse.getPassword()),
        () -> assertEquals(authoritiesExpected, userResponse.getAuthorities())
    );

  }

  @Test
  void ShouldMapUserDomainToUserModel() {
    // Arrange
    User userDomain = new User(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"), "userCommon", "password", Set.of(Role.USER));

    // Act
    UserModel userResponse = userMapper.toModel(userDomain);

    // Assert
    assertAll(
        () -> assertNotNull(userResponse),
        () -> assertEquals(userDomain.getId(), userResponse.getId()),
        () -> assertEquals(userDomain.getUsername(), userResponse.getUsername()),
        () -> assertEquals(userDomain.getPassword(), userResponse.getPassword()),
        () -> assertEquals(userDomain.getRoles(), userResponse.getRoles())
    );
  }

  @Test
  void ShouldMapUserDomainToUserResponseDTO(){
    // Arrange
    User userDomain = new User(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"), "userCommon", "password", Set.of(Role.USER));

    // Act
    UserResponseDTO userResponse = userMapper.toDTO(userDomain);

    // Assert
    assertAll(
        () -> assertNotNull(userResponse),
        () -> assertEquals(userDomain.getId(), userResponse.id()),
        () -> assertEquals(userDomain.getUsername(), userResponse.username()),
        () -> assertEquals(userDomain.getRoles(), userResponse.roles())
    );
  }

  @Test
  void ShouldMapUserRequestDTOToUserDomain(){
    // Arrange
    UserRequestDTO userRequestDTO = new UserRequestDTO("usernameCommon", "password@", Set.of("USER"));

    Set<Role> rolesExpected = userRequestDTO.roles().stream().map(Role::valueOf).collect(Collectors.toSet());

    // Act
    User userResponse = userMapper.toDomain(userRequestDTO);

    // Assert
    assertAll(
        () -> assertNotNull(userResponse),
        () -> assertEquals(userRequestDTO.username(), userResponse.getUsername()),
        () -> assertEquals(userRequestDTO.password(), userResponse.getPassword()),
        () -> assertEquals(rolesExpected, userResponse.getRoles())
    );
  }
}

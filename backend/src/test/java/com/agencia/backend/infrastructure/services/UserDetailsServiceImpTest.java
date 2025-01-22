package com.agencia.backend.infrastructure.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.application.useCase.user.FindUserByUsernameUseCase;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.infrastructure.model.UserDetailsImp;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImpTest {

  @InjectMocks
  private UserDetailsServiceImp userDetailsService;

  @Mock
  private FindUserByUsernameUseCase findUserByUsernameUseCase;

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

  @Test
  void ShouldReturnUserDetails() {
    // Arrange
    String username = "userCommon";
    User userDomain = creteUserDomain();

    UserDetailsImp mockUserDetails = new UserDetailsImp(
        userDomain.getId(), userDomain.getUsername(), userDomain.getPassword(), userDomain.getRoles()
    );

    when(findUserByUsernameUseCase.getUser(userDomain.getUsername())).thenReturn(userDomain);
    when(userMapper.toUserDetails(userDomain)).thenReturn(mockUserDetails);

    // Act
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

    // Assert
    assertNotNull(userDetails);
    assertEquals(userDomain.getUsername(), userDetails.getUsername());
    assertEquals(userDomain.getPassword(), userDetails.getPassword());

    verify(findUserByUsernameUseCase).getUser(userDomain.getUsername());
    verify(userMapper).toUserDetails(userDomain);
  }

}
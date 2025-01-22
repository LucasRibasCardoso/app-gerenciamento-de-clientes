package com.agencia.backend.infrastructure.services;

import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.application.useCase.user.FindUserByUsernameUseCase;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Implementação da interface UserDetailsService do Spring Security
 * para carregar os detalhes do usuário a partir do username.
 */
public class UserDetailsServiceImp implements UserDetailsService {

  private final FindUserByUsernameUseCase findUserByUsernameUseCase;
  private final UserMapper userMapper;

  public UserDetailsServiceImp(FindUserByUsernameUseCase findUserByUsernameUseCase, UserMapper userMapper) {
    this.findUserByUsernameUseCase = findUserByUsernameUseCase;
    this.userMapper = userMapper;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = findUserByUsernameUseCase.getUser(username);
    return userMapper.toUserDetails(user);
  }

}

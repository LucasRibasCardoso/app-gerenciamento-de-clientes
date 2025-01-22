package com.agencia.backend.infrastructure.configuration.bean.user;

import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.infrastructure.services.UserDetailsServiceImp;
import com.agencia.backend.application.useCase.user.FindUserByUsernameUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class UserDetailsServiceConfig {

  @Bean
  public UserDetailsService userDetailsService(
      FindUserByUsernameUseCase loadByUsernameUseCase,
      UserMapper userMapper
      ) {
    return new UserDetailsServiceImp(loadByUsernameUseCase, userMapper);
  }

}

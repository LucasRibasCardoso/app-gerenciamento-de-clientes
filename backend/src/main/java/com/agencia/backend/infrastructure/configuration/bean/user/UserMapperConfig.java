package com.agencia.backend.infrastructure.configuration.bean.user;

import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.presentation.mapper.user.implementation.UserMapperImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserMapperConfig {

  @Bean
  public UserMapper userMapper() {
    return new UserMapperImp();
  }
}

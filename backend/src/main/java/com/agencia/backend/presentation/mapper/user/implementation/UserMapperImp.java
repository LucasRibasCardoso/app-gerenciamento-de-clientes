package com.agencia.backend.presentation.mapper.user.implementation;

import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.presentation.dto.user.UserRequestDTO;
import com.agencia.backend.presentation.dto.user.UserResponseDTO;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.infrastructure.model.UserModel;
import com.agencia.backend.infrastructure.model.UserDetailsImp;
import java.util.stream.Collectors;

public class UserMapperImp implements UserMapper {

  @Override
  public User toDomain(UserModel model) {
    return new User(
        model.getId(),
        model.getUsername(),
        model.getPassword(),
        model.getRoles()
    );
  }

  @Override
  public UserDetailsImp toUserDetails(User domain) {
    return new UserDetailsImp(
        domain.getId(),
        domain.getUsername(),
        domain.getPassword(),
        domain.getRoles()
    );
  }

  @Override
  public UserModel toModel(User domain) {
    return new UserModel(
        domain.getId(),
        domain.getUsername(),
        domain.getPassword(),
        domain.getRoles()
    );
  }

  @Override
  public UserResponseDTO toDTO(User domain) {
    return new UserResponseDTO(
        domain.getId(),
        domain.getUsername(),
        domain.getRoles()
    );
  }

  @Override
  public User toDomain(UserRequestDTO dto) {
    return new User(
        null,
        dto.username(),
        dto.password(),
        dto.roles().stream().map(Role::valueOf).collect(Collectors.toSet())
    );
  }

}

package com.agencia.backend.presentation.mapper.user;

import com.agencia.backend.domain.entity.User;
import com.agencia.backend.infrastructure.model.UserModel;
import com.agencia.backend.infrastructure.model.UserDetailsImp;
import com.agencia.backend.presentation.dto.user.UserRequestDTO;
import com.agencia.backend.presentation.dto.user.UserResponseDTO;

public interface UserMapper {
  UserModel toModel(User domain);
  User toDomain(UserModel model);
  UserDetailsImp toUserDetails(User domain);
  UserResponseDTO toDTO(User domain);
  User toDomain(UserRequestDTO dto);
}

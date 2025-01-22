package com.agencia.backend.application.useCase.user;

import com.agencia.backend.domain.entity.User;
import java.util.List;

public interface FindAllUserUseCase {
  List<User> getAllUsers();
}

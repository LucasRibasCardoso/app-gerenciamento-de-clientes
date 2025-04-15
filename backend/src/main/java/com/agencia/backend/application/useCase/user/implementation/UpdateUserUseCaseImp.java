package com.agencia.backend.application.useCase.user.implementation;

import com.agencia.backend.application.services.UserExistenceValidationService;
import com.agencia.backend.application.services.UserFieldUpdateService;
import com.agencia.backend.application.useCase.user.FindUserByIdUseCase;
import com.agencia.backend.application.useCase.user.UpdateUserUseCase;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.repository.UserRepository;
import java.util.UUID;

public class UpdateUserUseCaseImp implements UpdateUserUseCase {

  private final UserRepository userRepository;
  private final FindUserByIdUseCase findUserByIdUseCase;
  private final UserFieldUpdateService userFieldUpdateService;
  private final UserExistenceValidationService userExistenceValidationService;

  public UpdateUserUseCaseImp(
      UserRepository userRepository,
      FindUserByIdUseCase findUserByIdUseCase,
      UserFieldUpdateService userFieldUpdateService,
      UserExistenceValidationService userExistenceValidationService
  ) {
    this.userRepository = userRepository;
    this.findUserByIdUseCase = findUserByIdUseCase;
    this.userFieldUpdateService = userFieldUpdateService;
    this.userExistenceValidationService = userExistenceValidationService;
  }

  @Override
  public User update(UUID id, User user) {
    // valida se o usuário existe
    userExistenceValidationService.validateUsername(user.getUsername());

    // Verifica se o usuário já existe
    User existingUser = findUserByIdUseCase.getUser(id);

    // Atualiza o usuário com os novos valores
    User userUpdated = userFieldUpdateService.updateUser(existingUser, user);

    // Salva o usuário atualizado no banco de dados
    return userRepository.save(userUpdated);
  }

}

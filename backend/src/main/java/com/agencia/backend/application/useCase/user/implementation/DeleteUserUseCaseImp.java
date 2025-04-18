package com.agencia.backend.application.useCase.user.implementation;

import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.exceptions.user.SelfDeletionException;
import com.agencia.backend.domain.exceptions.user.UserNotFoundException;
import com.agencia.backend.domain.repository.UserRepository;
import com.agencia.backend.application.useCase.user.DeleteUserUseCase;
import java.util.UUID;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class DeleteUserUseCaseImp implements DeleteUserUseCase {

  private final UserRepository userRepository;

  public DeleteUserUseCaseImp(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void deleteUser(UUID id) {
    if (!userRepository.existsById(id)) {
      throw new UserNotFoundException("Nenhum usuário encontrado.");
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null && authentication.isAuthenticated()) {
      UUID authenticatedUserId = getIdByUsername(authentication.getName());

      if (authenticatedUserId != null && authenticatedUserId.equals(id)) {
        throw new SelfDeletionException("Você não pode excluir seu próprio usuário.");
      }
    }

    userRepository.deleteById(id);
  }

  private UUID getIdByUsername(String username) {
    return userRepository.findByUsername(username)
        .map(User::getId)
        .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));
  }

}

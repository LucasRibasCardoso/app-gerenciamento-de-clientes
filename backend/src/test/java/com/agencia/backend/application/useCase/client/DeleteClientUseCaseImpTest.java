package com.agencia.backend.application.useCase.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.domain.exceptions.client.ClientNotFoundException;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.useCase.client.implementation.DeleteClientUseCaseImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DeleteClientUseCaseImpTest {

  @Mock
  private ClientRepository clientRepository;

  @InjectMocks
  private DeleteClientUseCaseImp deleteClientUseCase;

  @Test
  void ShouldNotThrowException_WhenClientExists() {
    // Arrange
    Long clientId = 1L;

    when(clientRepository.existsById(clientId)).thenReturn(true);

    // Act & Assert
    assertDoesNotThrow(() -> deleteClientUseCase.deleteClient(clientId));
    verify(clientRepository).deleteById(clientId);

  }

  @Test
  void ShouldThrowException_WhenClientDoesNotExist() {
    // Arrange
    Long clientId = 1L;

    when(clientRepository.existsById(clientId)).thenReturn(false);

    // Act & Assert
    ClientNotFoundException exception = assertThrows(ClientNotFoundException.class, () -> {
      deleteClientUseCase.deleteClient(clientId);
    });

    assertEquals("Nenhum cliente encontrado com o ID: " + clientId, exception.getMessage());
    verify(clientRepository, never()).deleteById(clientId);
  }

}
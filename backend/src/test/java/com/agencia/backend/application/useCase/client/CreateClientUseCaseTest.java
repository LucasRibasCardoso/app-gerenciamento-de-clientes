package com.agencia.backend.application.useCase.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.application.useCase.client.implementation.CreateClientUseCaseImp;
import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.domain.exceptions.client.ClientAlreadyExistsException;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.services.ClientExistenceValidationService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateClientUseCaseTest {

  @InjectMocks
  private CreateClientUseCaseImp createClientUseCaseImp;
  @Mock
  private ClientRepository clientRepository;
  @Mock
  private ClientExistenceValidationService clientExistenceValidationService;

  private Client createClientDomain(){
    return new Client(
        1L,
        "João da Silva",
        "123.123.123-12",
        LocalDate.of(1990, 1, 1),
        "(11) 98765-4321",
        "joao.silva@example.com",
        new Passport(
            "AB123456",
            LocalDate.of(2020, 6, 1),
            LocalDate.of(2030, 6, 1)
        ),
        new Address(
            "12345-678",
            "brasil",
            "sp",
            "são paulo",
            "jardim primavera",
            "Rua das flores",
            "Apto 101",
            "123"
        )
    );
  }

  @Test
  void ShouldCreateClientClient_WhenClientDoesNotExist() {
    // Arrange
    Client clientDomain = createClientDomain();
    Client clientSaved = createClientDomain();

    doNothing().when(clientExistenceValidationService).validateCpf(clientDomain);
    doNothing().when(clientExistenceValidationService).validateEmail(clientDomain);
    doNothing().when(clientExistenceValidationService).validatePassportNumber(clientDomain);

    when(clientRepository.save(any(Client.class))).thenReturn(clientSaved);

    // Act
    Client result = createClientUseCaseImp.createClient(clientDomain);

    // Assert
    assertNotNull(result);

    verify(clientExistenceValidationService).validateCpf(clientDomain);
    verify(clientExistenceValidationService).validateEmail(clientDomain);
    verify(clientExistenceValidationService).validatePassportNumber(clientDomain);
    verify(clientRepository).save(clientDomain);
  }

  @Test
  void ShouldThrowException_WhenCpfAlreadyRegistered() {
    // Arrange
    Client clientDomain = createClientDomain();

    doThrow(new ClientAlreadyExistsException("Esse CPF já está em uso."))
        .when(clientExistenceValidationService).validateCpf(clientDomain);

    // Act & Assert
    ClientAlreadyExistsException exception = assertThrows(ClientAlreadyExistsException.class, () ->
        createClientUseCaseImp.createClient(clientDomain)
    );

    assertEquals("Esse CPF já está em uso.", exception.getMessage());

    verify(clientExistenceValidationService).validateCpf(clientDomain);
    verify(clientExistenceValidationService, never()).validateEmail(any(Client.class));
    verify(clientExistenceValidationService, never()).validatePassportNumber(any(Client.class));
    verify(clientRepository, never()).save(any(Client.class));
  }

  @Test
  void ShouldThrowException_WhenEmailAlreadyRegistered() {
    // Arrange
    Client clientDomain = createClientDomain();

    doNothing().when(clientExistenceValidationService).validateCpf(clientDomain);
    doThrow(new ClientAlreadyExistsException("Esse email já está em uso."))
        .when(clientExistenceValidationService).validateEmail(clientDomain);


    // Act & Assert
    ClientAlreadyExistsException exception = assertThrows(ClientAlreadyExistsException.class, () ->
        createClientUseCaseImp.createClient(clientDomain)
    );

    assertEquals("Esse email já está em uso.", exception.getMessage());

    verify(clientExistenceValidationService).validateCpf(clientDomain);
    verify(clientExistenceValidationService).validateEmail(clientDomain);
    verify(clientExistenceValidationService, never()).validatePassportNumber(any(Client.class));
    verify(clientRepository, never()).save(any(Client.class));
  }

  @Test
  void ShouldThrowException_WhenPassportNumberAlreadyRegistered() {
    // Arrange
    Client clientDomain = createClientDomain();

    doNothing().when(clientExistenceValidationService).validateCpf(clientDomain);
    doNothing().when(clientExistenceValidationService).validateEmail(clientDomain);
    doThrow(new ClientAlreadyExistsException("Esse número de passaporte já está em uso."))
        .when(clientExistenceValidationService).validatePassportNumber(clientDomain);

    // Act & Assert
    ClientAlreadyExistsException exception = assertThrows(ClientAlreadyExistsException.class, () ->
        createClientUseCaseImp.createClient(clientDomain)
    );

    assertEquals("Esse número de passaporte já está em uso.", exception.getMessage());

    verify(clientExistenceValidationService).validateCpf(clientDomain);
    verify(clientExistenceValidationService).validateEmail(clientDomain);
    verify(clientExistenceValidationService).validatePassportNumber(clientDomain);
    verify(clientRepository, never()).save(any(Client.class));
  }

}
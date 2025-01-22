package com.agencia.backend.application.useCase.client;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.application.useCase.client.implementation.UpdateClientUseCaseImp;
import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.domain.exceptions.client.ClientAlreadyExistsException;
import com.agencia.backend.domain.exceptions.client.ClientNotFoundException;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.services.ClientExistenceValidationService;
import com.agencia.backend.application.services.ClientFieldUpdateService;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UpdateClientUseCaseImpTest {

  @InjectMocks
  private UpdateClientUseCaseImp updateClientUseCase;
  @Mock
  private ClientRepository clientRepository;
  @Mock
  private FindClientByIdUseCase findClientByIdUseCase;
  @Mock
  private ClientFieldUpdateService clientFieldUpdateService;
  @Mock
  private ClientExistenceValidationService clientExistenceValidationService;

  private Client createExistingClient() {
    return new Client(
        1L, "oldName", "123.123.123-12", LocalDate.of(1990, 1, 1), "(12) 12345-6789", "oldEmail",
        new Passport("AB123456", LocalDate.of(2020, 1, 1), LocalDate.of(2035, 1, 1)),
        new Address("112345-00", "oldCountry", "oldState", "oldCity", "oldNeighborhood", "oldStreet", "oldComplement", "321")
    );
  }
  private Client createClientUpdateRequest() {
    return new Client(
        null, "newName", "321.321.321-21", LocalDate.of(2000, 1, 1), "(21) 12345-1234", "newEmail",
        new Passport("BA987456", null, null),
        new Address("54321-12", "newCountry", "newState", "newCity", "newNeighborhood", "newStreet", "newComplement", "123")
    );
  }
  private Client createUpdateClient() {
    return new Client(
        1L, "newName", "321.321.321-21", LocalDate.of(2000, 1, 1), "(21) 12345-1234", "newEmail",
        new Passport("BA987456", LocalDate.of(2020, 1, 1), LocalDate.of(2035, 1, 1)),
        new Address("54321-12", "newCountry", "newState", "newCity", "newNeighborhood", "newStreet", "newComplement", "123")
    );
  }

  @Test
  void ShouldUpdateClientSuccessfully() {
    // Arrange
    Long clientId = 1L;
    Client existingClient = createExistingClient();
    Client clientRequest = createClientUpdateRequest();
    Client updatedClient = createUpdateClient();

    when(findClientByIdUseCase.getClient(clientId)).thenReturn(existingClient);
    doNothing().when(clientExistenceValidationService).validateEmail(clientRequest);
    doNothing().when(clientExistenceValidationService).validatePassportNumber(clientRequest);
    when(clientFieldUpdateService.updateClient(existingClient, clientRequest)).thenReturn(updatedClient);
    when(clientRepository.save(updatedClient)).thenReturn(updatedClient);

    // Act
    Client result = updateClientUseCase.update(clientId, clientRequest);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(updatedClient.getId(), result.getId()),
        () -> assertEquals(updatedClient.getCompleteName(), result.getCompleteName()),
        () -> assertEquals(updatedClient.getCpf(), result.getCpf()),
        () -> assertEquals(updatedClient.getBirthDate(), result.getBirthDate()),
        () -> assertEquals(updatedClient.getPhone(), result.getPhone()),
        () -> assertEquals(updatedClient.getEmail(), result.getEmail()),

        () -> assertEquals(updatedClient.getPassport().getNumber(), result.getPassport().getNumber()),
        () -> assertEquals(updatedClient.getPassport().getEmissionDate(), result.getPassport().getEmissionDate()),
        () -> assertEquals(updatedClient.getPassport().getExpirationDate(), result.getPassport().getExpirationDate()),

        () -> assertEquals(updatedClient.getAddress().getZipCode(), result.getAddress().getZipCode()),
        () -> assertEquals(updatedClient.getAddress().getCountry(), result.getAddress().getCountry()),
        () -> assertEquals(updatedClient.getAddress().getState(), result.getAddress().getState()),
        () -> assertEquals(updatedClient.getAddress().getCity(), result.getAddress().getCity()),
        () -> assertEquals(updatedClient.getAddress().getNeighborhood(), result.getAddress().getNeighborhood()),
        () -> assertEquals(updatedClient.getAddress().getStreet(), result.getAddress().getStreet()),
        () -> assertEquals(updatedClient.getAddress().getComplement(), result.getAddress().getComplement()),
        () -> assertEquals(updatedClient.getAddress().getResidentialNumber(), result.getAddress().getResidentialNumber())
    );
    verify(findClientByIdUseCase).getClient(clientId);
    verify(clientExistenceValidationService).validateEmail(clientRequest);
    verify(clientExistenceValidationService).validatePassportNumber(clientRequest);
    verify(clientFieldUpdateService).updateClient(existingClient, clientRequest);
    verify(clientRepository).save(updatedClient);
  }

  @Test
  void ShouldThrowException_WhenClientNotFound() {
    // Arrange
    Long clientId = 1L;
    Client clientRequest = createClientUpdateRequest();

    doThrow(new ClientNotFoundException("Nenhum cliente encontrado com o ID: " + clientId))
        .when(findClientByIdUseCase).getClient(clientId);

    // Act
    ClientNotFoundException exception = assertThrows(
        ClientNotFoundException.class, () -> updateClientUseCase.update(clientId, clientRequest));

    // Assert
    assertEquals("Nenhum cliente encontrado com o ID: " + clientId, exception.getMessage());

    verify(findClientByIdUseCase).getClient(clientId);
    verify(clientExistenceValidationService, never()).validateEmail(clientRequest);
    verify(clientExistenceValidationService, never()).validatePassportNumber(clientRequest);
    verify(clientFieldUpdateService, never()).updateClient(any(Client.class), any(Client.class));
    verify(clientRepository, never()).save(any(Client.class));
  }

  @Test
  void ShouldThrowException_WhenEmailAlreadyExists() {
    // Arrange
    Long clientId = 1L;
    Client existingClient = createExistingClient();
    Client clientRequest = createClientUpdateRequest();

    when(findClientByIdUseCase.getClient(clientId)).thenReturn(existingClient);
    doThrow(new ClientAlreadyExistsException("Esse email já está em uso."))
        .when(clientExistenceValidationService).validateEmail(clientRequest);

    // Act
    ClientAlreadyExistsException exception = assertThrows(
        ClientAlreadyExistsException.class, () -> updateClientUseCase.update(clientId, clientRequest));

    // Assert
    assertEquals("Esse email já está em uso.", exception.getMessage());

    verify(findClientByIdUseCase).getClient(clientId);
    verify(clientExistenceValidationService).validateEmail(clientRequest);
    verify(clientExistenceValidationService, never()).validatePassportNumber(clientRequest);
    verify(clientFieldUpdateService, never()).updateClient(any(Client.class), any(Client.class));
    verify(clientRepository, never()).save(any(Client.class));
  }

  @Test
  void ShouldThrowException_WhenPassportNumberAlreadyExists() {
    // Arrange
    Long clientId = 1L;
    Client existingClient = createExistingClient();
    Client clientRequest = createClientUpdateRequest();

    when(findClientByIdUseCase.getClient(clientId)).thenReturn(existingClient);
    doNothing().when(clientExistenceValidationService).validateEmail(clientRequest);
    doThrow(new ClientAlreadyExistsException("Esse número de passaporte já está em uso."))
        .when(clientExistenceValidationService).validatePassportNumber(clientRequest);

    // Act
    ClientAlreadyExistsException exception = assertThrows(
        ClientAlreadyExistsException.class, () -> updateClientUseCase.update(clientId, clientRequest));

    // Assert
    assertEquals("Esse número de passaporte já está em uso.", exception.getMessage());

    verify(findClientByIdUseCase).getClient(clientId);
    verify(clientExistenceValidationService).validateEmail(clientRequest);
    verify(clientExistenceValidationService).validatePassportNumber(clientRequest);
    verify(clientFieldUpdateService, never()).updateClient(any(Client.class), any(Client.class));
    verify(clientRepository, never()).save(any(Client.class));
  }


}
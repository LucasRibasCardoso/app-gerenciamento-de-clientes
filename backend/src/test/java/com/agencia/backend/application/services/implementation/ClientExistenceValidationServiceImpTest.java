package com.agencia.backend.application.services.implementation;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.domain.exceptions.client.ClientAlreadyExistsException;
import com.agencia.backend.domain.repository.ClientRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientExistenceValidationServiceImpTest {

  @InjectMocks
  private ClientExistenceValidationServiceImp clientExistenceValidationService;
  @Mock
  private ClientRepository clientRepository;

  private Client createClientDomain(){
    return new Client(
        null,
        "João Da Silva",
        "497.494.050-30",
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
            "Brasil",
            "SP",
            "São Paulo",
            "Jardim Primavera",
            "Rua Das Flores",
            "Apto 101",
            "123"
        )
    );
  }

  @Test
  void validateCpf_ShouldThrowException_WhenCpfExists() {
    // Arrange
    Client client = createClientDomain();
    when(clientRepository.existsByCpf(client.getCpf())).thenReturn(true);

    // Act & Assert
    assertThrows(ClientAlreadyExistsException.class, () -> clientExistenceValidationService.validateCpf(client));
    verify(clientRepository).existsByCpf(client.getCpf());
  }

  @Test
  void validateCpf_ShouldNotThrowException_WhenCpfDoesNotExist() {
    // Arrange
    Client client = createClientDomain();
    when(clientRepository.existsByCpf(client.getCpf())).thenReturn(false);

    // Act
    clientExistenceValidationService.validateCpf(client);

    // Assert
    verify(clientRepository).existsByCpf(client.getCpf());
  }

  @Test
  void validatePassportNumber_ShouldThrowException_WhenPassportExists() {
    // Arrange
    Client client = createClientDomain();
    when(clientRepository.existsByPassportNumber(client.getPassport().getNumber())).thenReturn(true);

    // Act & Assert
    assertThrows(ClientAlreadyExistsException.class, () -> clientExistenceValidationService.validatePassportNumber(client));
    verify(clientRepository).existsByPassportNumber(client.getPassport().getNumber());
  }

  @Test
  void validatePassportNumber_ShouldNotThrowException_WhenPassportDoesNotExist() {
    // Arrange
    Client client = createClientDomain();
    when(clientRepository.existsByPassportNumber(client.getPassport().getNumber())).thenReturn(false);

    // Act
    clientExistenceValidationService.validatePassportNumber(client);

    // Assert
    verify(clientRepository).existsByPassportNumber(client.getPassport().getNumber());
  }

  @Test
  void validateEmail_ShouldThrowException_WhenEmailExists() {
    // Arrange
    Client client = createClientDomain();
    when(clientRepository.existsByEmail(client.getEmail())).thenReturn(true);

    // Act & Assert
    assertThrows(ClientAlreadyExistsException.class, () -> clientExistenceValidationService.validateEmail(client));
    verify(clientRepository).existsByEmail(client.getEmail());
  }

  @Test
  void validateEmail_ShouldNotThrowException_WhenEmailDoesNotExist() {
    // Arrange
    Client client = createClientDomain();
    when(clientRepository.existsByEmail(client.getEmail())).thenReturn(false);

    // Act
    clientExistenceValidationService.validateEmail(client);

    // Assert
    verify(clientRepository).existsByEmail(client.getEmail());
  }

}
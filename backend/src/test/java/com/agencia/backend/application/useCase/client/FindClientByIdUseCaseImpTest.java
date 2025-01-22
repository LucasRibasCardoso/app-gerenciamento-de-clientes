package com.agencia.backend.application.useCase.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.domain.exceptions.client.ClientNotFoundException;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.application.useCase.client.implementation.FindClientByIdUseCaseImp;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindClientByIdUseCaseImpTest {

  @Mock
  private ClientRepository clientRepository;

  @InjectMocks
  private FindClientByIdUseCaseImp findClientByIdUseCaseImp;

  private Client createClientDomain(){
    return new Client(
        1L,
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
            "Sp",
            "São Paulo",
            "Jardim Primavera",
            "Rua Das Flores",
            "Apto 101",
            "123"
        )
    );
  }

  @Test
  void ShouldReturnClient_WhenClientExists() {
    // Arrange
    Long clientId = 1L;
    Client client = createClientDomain();
    when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

    // Act
    Client response = findClientByIdUseCaseImp.getClient(clientId);

    // Assert
    assertAll(
        () -> assertNotNull(response),
        () -> assertEquals(client.getId(), response.getId()),
        () -> assertEquals(client.getCompleteName(), response.getCompleteName()),
        () -> assertEquals(client.getCpf(), response.getCpf()),
        () -> assertEquals(client.getBirthDate(), response.getBirthDate()),
        () -> assertEquals(client.getPhone(), response.getPhone()),
        () -> assertEquals(client.getEmail(), response.getEmail()),
        () -> assertEquals(client.getPassport().getNumber(), response.getPassport().getNumber()),
        () -> assertEquals(client.getPassport().getEmissionDate(), response.getPassport().getEmissionDate()),
        () -> assertEquals(client.getPassport().getExpirationDate(), response.getPassport().getExpirationDate()),
        () -> assertEquals(client.getAddress().getZipCode(), response.getAddress().getZipCode()),
        () -> assertEquals(client.getAddress().getCountry(), response.getAddress().getCountry()),
        () -> assertEquals(client.getAddress().getState(), response.getAddress().getState()),
        () -> assertEquals(client.getAddress().getCity(), response.getAddress().getCity()),
        () -> assertEquals(client.getAddress().getNeighborhood(), response.getAddress().getNeighborhood()),
        () -> assertEquals(client.getAddress().getStreet(), response.getAddress().getStreet()),
        () -> assertEquals(client.getAddress().getComplement(), response.getAddress().getComplement()),
        () -> assertEquals(client.getAddress().getResidentialNumber(), response.getAddress().getResidentialNumber())
    );

    verify(clientRepository).findById(clientId);

  }

  @Test
  void ShouldThrowException_WhenClientDoesNotExist() {
    // Arrange
    Long clientId = 1L;
    when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

    // Act and Assert
    ClientNotFoundException exception = assertThrows(ClientNotFoundException.class, () -> findClientByIdUseCaseImp.getClient(clientId));

    assertEquals("Nenhum cliente encontrado com o ID: " + clientId, exception.getMessage());
    verify(clientRepository).findById(clientId);
  }
}
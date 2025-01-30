package com.agencia.backend.application.useCase.client;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.agencia.backend.application.useCase.client.implementation.FindAllClientUseCaseImp;
import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.domain.repository.ClientRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class FindAllClientUseCaseImpTest {

  @Mock
  private ClientRepository clientRepository;

  @InjectMocks
  private FindAllClientUseCaseImp findAllClientUseCaseImp;

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
  void ShouldReturnListOfClients_WhenClientsExist() {
    // Arrange
    List<Client> clients = new ArrayList<>();
    clients.add(createClientDomain());

    String search = null;
    String orderBy = "id";
    String sortOrder = "desc";
    int page = 0;
    int size = 10;

    Page<Client> clientPage = new PageImpl<>(clients, PageRequest.of(page, size), clients.size());

    when(clientRepository.findAll(search, orderBy, sortOrder, page, size)).thenReturn(clientPage);

    // Act
    Page<Client> result = findAllClientUseCaseImp.getClients(search, orderBy, sortOrder, page, size);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(1, result.getTotalElements()),
        () -> assertEquals(1, result.getTotalPages()),
        () -> assertEquals(clients, result.getContent())
    );
  }

  @Test
  void ShouldReturnEmptyList_WhenNoClientsExist() {
    // Arrange
    List<Client> clients = new ArrayList<>();
    String search = null;
    String orderBy = "id";
    String sortOrder = "desc";
    int page = 0;
    int size = 10;

    Page<Client> clientPage = new PageImpl<>(clients, PageRequest.of(page, size), clients.size());

    when(clientRepository.findAll(search, orderBy, sortOrder, page, size)).thenReturn(clientPage);

    // Act
    Page<Client> result = findAllClientUseCaseImp.getClients(search, orderBy, sortOrder, page, size);

    // Assert
    assertAll(
        () -> assertNotNull(result),
        () -> assertEquals(0, result.getTotalElements()),
        () -> assertEquals(0, result.getTotalPages()),
        () -> assertEquals(clients, result.getContent())
    );
  }
}
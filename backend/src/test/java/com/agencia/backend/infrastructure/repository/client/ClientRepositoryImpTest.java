package com.agencia.backend.infrastructure.repository.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.infrastructure.model.ClientModel;
import com.agencia.backend.infrastructure.repository.client.implementation.ClientRepositoryImp;
import com.agencia.backend.infrastructure.specifications.SpecificationBuilder;
import com.agencia.backend.presentation.mapper.client.ClientMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class ClientRepositoryImpTest {

  @InjectMocks
  private ClientRepositoryImp clientRepository;
  @Mock
  private ClientJpaRepository clientJpaRepository;
  @Mock
  private ClientMapper clientMapper;
  @Mock
  private SpecificationBuilder<ClientModel> specificationBuilder;


  private ClientModel createClientModel() {
    return new ClientModel(
        1L,
        "João da Silva",
        "497.494.050-30",
        LocalDate.of(1990, 1, 1),
        "(11) 98765-4321",
        "joao.silva@example.com",
        "AB123456",
        LocalDate.of(2020, 6, 1),
        LocalDate.of(2030, 6, 1),
        "12345-678",
        "Brasil",
        "SP",
        "São Paulo",
        "Jardim Primavera",
        "Rua das Flores",
        "Apto 101",
        "123"
    );
  }

  private Client createClientDomain(){
    return new Client(
        1L,
        "João da Silva",
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
            "Rua das Flores",
            "Apto 101",
            "123"
        )
    );
  }


  @Test
  void ShouldSaveAClientInDatabase() {
    // Arrange
    Client clientDomain = createClientDomain();
    ClientModel clientModel = createClientModel();

    when(clientMapper.toModel(clientDomain)).thenReturn(clientModel);
    when(clientMapper.toDomain(clientModel)).thenReturn(clientDomain);
    when(clientJpaRepository.save(any(ClientModel.class))).thenReturn(clientModel);

    // Act
    Client savedClient = clientRepository.save(clientDomain);

    // Assert
    assertAll(
        () -> assertNotNull(savedClient),
        () -> assertEquals(clientDomain.getId(), savedClient.getId()),
        () -> assertEquals(clientDomain.getCompleteName(), savedClient.getCompleteName()),
        () -> assertEquals(clientDomain.getCpf(), savedClient.getCpf()),
        () -> assertEquals(clientDomain.getBirthDate(), savedClient.getBirthDate()),
        () -> assertEquals(clientDomain.getPhone(), savedClient.getPhone()),
        () -> assertEquals(clientDomain.getEmail(), savedClient.getEmail()),
        () -> assertEquals(clientDomain.getPassport().getNumber(), savedClient.getPassport().getNumber()),
        () -> assertEquals(clientDomain.getPassport().getEmissionDate(), savedClient.getPassport().getEmissionDate()),
        () -> assertEquals(clientDomain.getPassport().getExpirationDate(), savedClient.getPassport().getExpirationDate()),
        () -> assertEquals(clientDomain.getAddress().getZipCode(), savedClient.getAddress().getZipCode()),
        () -> assertEquals(clientDomain.getAddress().getCountry(), savedClient.getAddress().getCountry()),
        () -> assertEquals(clientDomain.getAddress().getState(), savedClient.getAddress().getState()),
        () -> assertEquals(clientDomain.getAddress().getCity(), savedClient.getAddress().getCity()),
        () -> assertEquals(clientDomain.getAddress().getNeighborhood(), savedClient.getAddress().getNeighborhood()),
        () -> assertEquals(clientDomain.getAddress().getStreet(), savedClient.getAddress().getStreet()),
        () -> assertEquals(clientDomain.getAddress().getComplement(), savedClient.getAddress().getComplement()),
        () -> assertEquals(clientDomain.getAddress().getResidentialNumber(), savedClient.getAddress().getResidentialNumber())
    );
    verify(clientJpaRepository).save(any(ClientModel.class));
    verify(clientMapper).toModel(clientDomain);
  }

  @Test
  void ShouldReturnOptionalClient_WhenClientExists() {
    // Arrange
    Long clientId = 1L;
    ClientModel clientModel = createClientModel();
    Client clientDomain = createClientDomain();

    when(clientJpaRepository.findById(clientId)).thenReturn(Optional.of(clientModel));
    when(clientMapper.toDomain(clientModel)).thenReturn(clientDomain);

    // Act
    Optional<Client> client = clientRepository.findById(clientId);

    // Assert
    assertAll(
        () -> assertTrue(client.isPresent()),
        () -> assertEquals(clientModel.getId(), client.get().getId()),
        () -> assertEquals(clientModel.getCompleteName(), client.get().getCompleteName()),
        () -> assertEquals(clientModel.getCpf(), client.get().getCpf()),
        () -> assertEquals(clientModel.getBirthDate(), client.get().getBirthDate()),
        () -> assertEquals(clientModel.getPhone(), client.get().getPhone()),
        () -> assertEquals(clientModel.getEmail(), client.get().getEmail()),
        () -> assertEquals(clientModel.getPassportNumber(), client.get().getPassport().getNumber()),
        () -> assertEquals(clientModel.getPassportEmissionDate(), client.get().getPassport().getEmissionDate()),
        () -> assertEquals(clientModel.getPassportExpirationDate(), client.get().getPassport().getExpirationDate()),
        () -> assertEquals(clientModel.getZipCode(), client.get().getAddress().getZipCode()),
        () -> assertEquals(clientModel.getCountry(), client.get().getAddress().getCountry()),
        () -> assertEquals(clientModel.getState(), client.get().getAddress().getState()),
        () -> assertEquals(clientModel.getCity(), client.get().getAddress().getCity()),
        () -> assertEquals(clientModel.getNeighborhood(), client.get().getAddress().getNeighborhood()),
        () -> assertEquals(clientModel.getStreet(), client.get().getAddress().getStreet()),
        () -> assertEquals(clientModel.getComplement(), client.get().getAddress().getComplement()),
        () -> assertEquals(clientModel.getResidentialNumber(), client.get().getAddress().getResidentialNumber())

    );
    verify(clientJpaRepository).findById(clientId);
    verify(clientMapper).toDomain(clientModel);
 }

  @Test
  void ShouldReturnEmptyOptional_WhenClientDoesNotExist() {
    // Arrange
    Long clientId = 1L;


    when(clientJpaRepository.findById(clientId)).thenReturn(Optional.empty());

    // Act
    Optional<Client> client = clientRepository.findById(clientId);

    // Assert
    assertAll(
        () -> assertNotNull(client),
        () -> assertTrue(client.isEmpty())
    );
    verify(clientJpaRepository).findById(clientId);
    verify(clientMapper, never()).toDomain(any(ClientModel.class));
  }

  @Test
  void ShouldFindAllClients_WhenClientsExist() {
    // Arrange
    String search = "name:John";
    String orderBy = "name";
    String sortOrder = "ASC";
    int page = 0;
    int size = 5;

    // Configuração do Sort e Pageable
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), orderBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    // Configuração da Specification
    Specification<ClientModel> specification = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

    // Criação dos objetos de teste
    ClientModel clientModel = createClientModel();
    Client client = createClientDomain();

    // Configuração do Page<ClientModel>
    Page<ClientModel> pageResult = new PageImpl<>(List.of(clientModel));

    // Mocking dos comportamentos esperados
    when(specificationBuilder.build(search)).thenReturn(specification);
    when(clientJpaRepository.findAll(specification, pageable)).thenReturn(pageResult);
    when(clientMapper.toDomain(clientModel)).thenReturn(client);

    // Act
    Page<Client> result = clientRepository.findAll(search, orderBy, sortOrder, page, size);

    // Assert
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    assertEquals(client, result.getContent().get(0));

    // Verificação dos mocks
    verify(specificationBuilder).build(search);
    verify(clientJpaRepository).findAll(specification, pageable);
    verify(clientMapper).toDomain(clientModel);
  }

  @Test
  void ShouldReturnEmptyList_WhenNoClientsExist() {
    // Arrange
    String search = "nonExistentField:value";
    String orderBy = "name";
    String sortOrder = "ASC";
    int page = 0;
    int size = 5;

    // Configuração do Sort e Pageable
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), orderBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    // Configuração da Specification
    Specification<ClientModel> specification = (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();

    // Configuração do Page<ClientModel> vazio
    Page<ClientModel> pageResult = new PageImpl<>(Collections.emptyList());

    // Mocking dos comportamentos esperados
    when(specificationBuilder.build(search)).thenReturn(specification);
    when(clientJpaRepository.findAll(specification, pageable)).thenReturn(pageResult);

    // Act
    Page<Client> result = clientRepository.findAll(search, orderBy, sortOrder, page, size);

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());
    assertEquals(0, result.getTotalElements());

    // Verificação dos mocks
    verify(specificationBuilder).build(search);
    verify(clientJpaRepository).findAll(specification, pageable);
    verify(clientMapper, never()).toDomain(any(ClientModel.class));
  }
  @Test
  void ShouldReturnTrue_WhenClientByCpfExists() {
    // Arrange
    String cpf = "12345678901";

    // Act
    when(clientJpaRepository.existsByCpf(cpf)).thenReturn(true);

    boolean exists = clientRepository.existsByCpf(cpf);

    // Assert
    assertTrue(exists);
    verify(clientJpaRepository).existsByCpf(cpf);
  }

  @Test
  void ShouldReturnTrue_WhenClientByPassportNumberExists() {
    // Arrange
    String passportNumber = "ABC123456";

    // Act
    when(clientJpaRepository.existsByPassportNumber(passportNumber)).thenReturn(true);

    boolean exists = clientRepository.existsByPassportNumber(passportNumber);

    // Assert
    assertTrue(exists);
    verify(clientJpaRepository).existsByPassportNumber(passportNumber);
  }

  @Test
  void ShouldReturnTrue_WhenClientByEmailExists() {
    // Arrange
    String email = "example@gmail.com";

    // Act
    when(clientJpaRepository.existsByEmail(email)).thenReturn(true);

    boolean exists = clientRepository.existsByEmail(email);

    // Assert
    assertTrue(exists);
    verify(clientJpaRepository).existsByEmail(email);
  }

  @Test
  void ShouldReturnTrue_WhenClientByIdExists() {
    // Arrange
    Long id = 1L;

    // Act
    when(clientJpaRepository.existsById(id)).thenReturn(true);

    boolean exists = clientRepository.existsById(id);

    // Assert
    assertTrue(exists);
    verify(clientJpaRepository).existsById(id);
  }

  @Test
  void ShouldDeleteClientById() {
    // Arrange
    Long id = 1L;

    // Act
    clientRepository.deleteById(id);

    // Assert
    verify(clientJpaRepository, times(1)).deleteById(id);
  }
}
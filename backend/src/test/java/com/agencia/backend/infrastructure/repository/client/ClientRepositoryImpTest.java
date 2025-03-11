package com.agencia.backend.infrastructure.repository.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.application.services.EncryptionService;
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
  private ClientRepositoryImp clientRepositoryImp;
  @Mock
  private ClientJpaRepository jpaRepository;
  @Mock
  private ClientMapper clientMapper;
  @Mock
  private SpecificationBuilder<ClientModel> specificationBuilder;
  @Mock
  private EncryptionService encryptionService;

  // Métodos auxiliares
  private void mockDecryptionForAllFields(ClientModel model) {
    when(encryptionService.decrypt(model.getRawCpf())).thenReturn("123.456.789-00");
    when(encryptionService.decrypt(model.getRawPhone())).thenReturn("(11) 99999-9999");
    when(encryptionService.decrypt(model.getRawPassportNumber())).thenReturn("ABC123");
    when(encryptionService.decrypt(model.getRawZipCode())).thenReturn("12345-678");
    when(encryptionService.decrypt(model.getRawCountry())).thenReturn("Brasil");
    when(encryptionService.decrypt(model.getRawState())).thenReturn("SP");
    when(encryptionService.decrypt(model.getRawCity())).thenReturn("São Paulo");
    when(encryptionService.decrypt(model.getRawNeighborhood())).thenReturn("Centro");
    when(encryptionService.decrypt(model.getRawStreet())).thenReturn("Rua Principal");
    when(encryptionService.decrypt(model.getRawComplement())).thenReturn("Apto 101");
    when(encryptionService.decrypt(model.getRawResidentialNumber())).thenReturn("123");
  }

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

  private ClientModel createEncryptedClientModel() {
    ClientModel model = createClientModel();
    model.setRawCpf("encryptedCpf");
    model.setRawPhone("encryptedPhone");
    model.setRawPassportNumber("encryptedPassport");
    model.setRawZipCode("encryptedZip");
    model.setRawCountry("encryptedCountry");
    model.setRawState("encryptedState");
    model.setRawCity("encryptedCity");
    model.setRawNeighborhood("encryptedNeighborhood");
    model.setRawStreet("encryptedStreet");
    model.setRawComplement("encryptedComplement");
    model.setRawResidentialNumber("encryptedNumber");
    return model;
  }

  @Test
  void ShouldSaveClientWithAllEncryptedFields() {
    // Arrange
    Client clientDomain = createClientDomain();
    ClientModel decryptedModel = createClientModel();
    ClientModel encryptedModel = createEncryptedClientModel();

    when(clientMapper.toModel(clientDomain)).thenReturn(decryptedModel);
    when(jpaRepository.save(decryptedModel)).thenReturn(encryptedModel);
    when(clientMapper.toDomain(encryptedModel)).thenReturn(clientDomain);

    // Mockar todas as operações de criptografia
    when(encryptionService.encrypt(clientDomain.getCpf())).thenReturn("encryptedCpf");
    when(encryptionService.hash(clientDomain.getCpf())).thenReturn("hashedCpf");
    when(encryptionService.encrypt(clientDomain.getPhone())).thenReturn("encryptedPhone");
    when(encryptionService.encrypt(clientDomain.getPassport().getNumber())).thenReturn("encryptedPassport");
    when(encryptionService.hash(clientDomain.getPassport().getNumber())).thenReturn("hashedPassport");
    when(encryptionService.encrypt(clientDomain.getAddress().getZipCode())).thenReturn("encryptedZip");
    when(encryptionService.encrypt(clientDomain.getAddress().getCountry())).thenReturn("encryptedCountry");
    when(encryptionService.encrypt(clientDomain.getAddress().getState())).thenReturn("encryptedState");
    when(encryptionService.encrypt(clientDomain.getAddress().getCity())).thenReturn("encryptedCity");
    when(encryptionService.encrypt(clientDomain.getAddress().getNeighborhood())).thenReturn("encryptedNeighborhood");
    when(encryptionService.encrypt(clientDomain.getAddress().getStreet())).thenReturn("encryptedStreet");
    when(encryptionService.encrypt(clientDomain.getAddress().getComplement())).thenReturn("encryptedComplement");
    when(encryptionService.encrypt(clientDomain.getAddress().getResidentialNumber())).thenReturn("encryptedResidentialNumber");

    // Act
    Client savedClient = clientRepositoryImp.save(clientDomain);

    // Assert
    assertAll(
        () -> assertNotNull(savedClient),
        () -> assertEquals(decryptedModel.getId(), savedClient.getId()),
        () -> assertEquals(decryptedModel.getCompleteName(), savedClient.getCompleteName()),
        () -> assertEquals(decryptedModel.getRawCpf(), savedClient.getCpf()),
        () -> assertEquals(decryptedModel.getBirthDate(), savedClient.getBirthDate()),
        () -> assertEquals(decryptedModel.getRawPhone(), savedClient.getPhone()),
        () -> assertEquals(decryptedModel.getEmail(), savedClient.getEmail()),
        () -> assertEquals(decryptedModel.getRawPassportNumber(), savedClient.getPassport().getNumber()),
        () -> assertEquals(decryptedModel.getPassportEmissionDate(), savedClient.getPassport().getEmissionDate()),
        () -> assertEquals(decryptedModel.getPassportExpirationDate(), savedClient.getPassport().getExpirationDate()),
        () -> assertEquals(decryptedModel.getRawZipCode(), savedClient.getAddress().getZipCode()),
        () -> assertEquals(decryptedModel.getRawCountry(), savedClient.getAddress().getCountry()),
        () -> assertEquals(decryptedModel.getRawState(), savedClient.getAddress().getState()),
        () -> assertEquals(decryptedModel.getRawCity(), savedClient.getAddress().getCity()),
        () -> assertEquals(decryptedModel.getRawNeighborhood(), savedClient.getAddress().getNeighborhood()),
        () -> assertEquals(decryptedModel.getRawStreet(), savedClient.getAddress().getStreet()),
        () -> assertEquals(decryptedModel.getRawComplement(), savedClient.getAddress().getComplement()),
        () -> assertEquals(decryptedModel.getRawResidentialNumber(), savedClient.getAddress().getResidentialNumber())
    );

    verify(jpaRepository).save(decryptedModel);

    verify(encryptionService, times(11)).encrypt(anyString());
    verify(encryptionService, times(2)).hash(anyString());
  }


  @Test
  void ShouldReturnClient_WhenClientExists() {
    // Arrange
    Long existentId = 1L;
    ClientModel encryptedModel = createEncryptedClientModel();
    Client expectedClient = createClientDomain();

    when(jpaRepository.findById(existentId))
        .thenReturn(Optional.of(encryptedModel));

    // Configurar descriptografia para todos os campos
    mockDecryptionForAllFields(encryptedModel);

    when(clientMapper.toDomain(any(ClientModel.class)))
        .thenReturn(expectedClient);

    // Act
    Optional<Client> result = clientRepositoryImp.findById(existentId);

    // Assert
    assertAll(
        () -> assertTrue(result.isPresent()),
        () -> assertEquals(expectedClient, result.get())
    );

    // Verificação de interações
    verify(jpaRepository).findById(existentId);
    verify(clientMapper).toDomain(encryptedModel);

    // 11 chamadas de descriptografia no método toDecryptedClient na classe ClientRepositoryImp
    verify(encryptionService, times(11)).decrypt(anyString());
  }

  @Test
  void ShouldReturnEmptyOptional_WhenClientDoesNotExist() {
    // Arrange
    Long clientId = 1L;

    when(jpaRepository.findById(clientId)).thenReturn(Optional.empty());

    // Act
    Optional<Client> client = clientRepositoryImp.findById(clientId);

    // Assert
    assertAll(
        () -> assertNotNull(client),
        () -> assertTrue(client.isEmpty())
    );
    verify(jpaRepository).findById(clientId);
    verify(clientMapper, never()).toDomain(any(ClientModel.class));
  }

  @Test
  void ShouldReturnListOfClients_WhenClientsExist() {
    ClientModel encryptedModel = createEncryptedClientModel();
    Page<ClientModel> page = new PageImpl<>(List.of(encryptedModel));

    when(specificationBuilder.build(any())).thenReturn((root, query, cb) -> cb.conjunction());

    // Corrigindo a linha ambígua
    when(jpaRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

    when(encryptionService.decrypt(any())).thenAnswer(inv -> inv.getArgument(0)
        .toString()
        .replace("encrypted", ""));

    Page<Client> result = clientRepositoryImp.findAll("query", "name", "ASC", 0, 10);

    assertEquals(1, result.getContent()
        .size());
    verify(jpaRepository).findAll(any(Specification.class), any(Pageable.class));
  }

  @Test
  void ShouldReturnEmptyList_WhenNoClientsExist() {
    // Arrange
    String search = "nonExistentField:value";
    String orderBy = "completeName";
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
    when(jpaRepository.findAll(specification, pageable)).thenReturn(pageResult);

    // Act
    Page<Client> result = clientRepositoryImp.findAll(search, orderBy, sortOrder, page, size);

    // Assert
    assertNotNull(result);
    assertTrue(result.isEmpty());
    assertEquals(0, result.getTotalElements());

    // Verificação dos mocks
    verify(specificationBuilder).build(search);
    verify(jpaRepository).findAll(specification, pageable);
    verify(clientMapper, never()).toDomain(any(ClientModel.class));
  }

  @Test
  void ShouldReturnTrue_WhenClientByCpfExists() {
    // Arrange
    String cpf = "12345678901";
    String hashedCpf = "hashedCpf";

    // Act
    when(encryptionService.hash(cpf)).thenReturn(hashedCpf);
    when(jpaRepository.existsByHashedCpf(hashedCpf)).thenReturn(true);

    boolean isExist = clientRepositoryImp.existsByCpf(cpf);

    // Assert
    assertTrue(isExist);
    verify(jpaRepository).existsByHashedCpf(hashedCpf);

  }

  @Test
  void ShouldReturnTrue_WhenClientByPassportNumberExists() {
    // Arrange
    String passportNumber = "ABC123456";
    String hashedPassportNumber = "hashedPassportNumber";

    // Act
    when(encryptionService.hash(passportNumber)).thenReturn(hashedPassportNumber);
    when(jpaRepository.existsByHashedPassportNumber(hashedPassportNumber)).thenReturn(true);

    boolean isExist = clientRepositoryImp.existsByPassportNumber(passportNumber);

    // Assert
    assertTrue(isExist);
    verify(jpaRepository).existsByHashedPassportNumber(hashedPassportNumber);
    verify(encryptionService).hash(passportNumber);
  }

  @Test
  void ShouldReturnTrue_WhenClientByEmailExists() {
    // Arrange
    String email = "example@gmail.com";

    // Act
    when(jpaRepository.existsByEmail(email)).thenReturn(true);

    boolean exists = clientRepositoryImp.existsByEmail(email);

    // Assert
    assertTrue(exists);
    verify(jpaRepository).existsByEmail(email);
  }

  @Test
  void ShouldReturnTrue_WhenClientByIdExists() {
    // Arrange
    Long id = 1L;

    // Act
    when(jpaRepository.existsById(id)).thenReturn(true);

    boolean exists = clientRepositoryImp.existsById(id);

    // Assert
    assertTrue(exists);
    verify(jpaRepository).existsById(id);
  }

  @Test
  void ShouldDeleteClientById() {
    // Arrange
    Long id = 1L;

    // Act
    clientRepositoryImp.deleteById(id);

    // Assert
    verify(jpaRepository, times(1)).deleteById(id);
  }
}
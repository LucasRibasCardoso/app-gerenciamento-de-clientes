package com.agencia.backend.presentation.mapper.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.agencia.backend.presentation.dto.address.AddressDTO;
import com.agencia.backend.presentation.dto.client.ClientRequestDTO;
import com.agencia.backend.presentation.dto.client.ClientRequestUpdateDTO;
import com.agencia.backend.presentation.dto.client.ClientResponseDTO;
import com.agencia.backend.presentation.dto.passport.PassportDTO;
import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.infrastructure.model.ClientModel;
import com.agencia.backend.presentation.mapper.client.implementation.AddressMapperImp;
import com.agencia.backend.presentation.mapper.client.implementation.ClientMapperImp;
import com.agencia.backend.presentation.mapper.client.implementation.PassportMapperImp;
import com.agencia.backend.presentation.utils.dateConverter.DateConverter;
import com.agencia.backend.presentation.utils.textFormatter.TextFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientMapperTest {

  private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  @InjectMocks
  private ClientMapperImp clientMapper;
  @Mock
  private PassportMapperImp passportMapper;
  @Mock
  private AddressMapperImp addressMapper;
  @Mock
  private TextFormatter textFormatter;
  @Mock
  private DateConverter dateConverter;

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
  private ClientRequestDTO createClientRequestDTO() {
    PassportDTO passportRequest = new PassportDTO("AB123456", "01/06/2020", "01/06/2030");

    AddressDTO addressRequest = new AddressDTO("12345-678", "Brasil", "SP", "São Paulo", "Jardim Primavera",
        "Rua Das Flores", "Apto 101", "123"
    );

    ClientRequestDTO clientRequest = new ClientRequestDTO("João Da Silva", "497.494.050-30", "01/01/1990",
        "(11) 98765-4321", "joao.silva@example.com", passportRequest, addressRequest
    );

    return clientRequest;
  }
  private ClientModel createClientModel() {
    return new ClientModel(
        1L,
        "João da Silva",
        "497.494.050-30",
        LocalDate.parse("15/05/1990", DATE_PATTERN),
        "(11) 98765-4321",
        "joao.silva@example.com",
        "AB123456",
        LocalDate.parse("01/06/2020", DATE_PATTERN),
        LocalDate.parse("01/06/2030", DATE_PATTERN),
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

  @Test
  void ShouldMapClientDtoToDomain() {
    // Arrange
    ClientRequestDTO clientRequest = createClientRequestDTO();

    Passport passportExpected = new Passport(
        "AB123456",
        LocalDate.of(2020, 6, 1),
        LocalDate.of(2030, 6, 1)
    );

    Address addressExpected = new Address(
        "12345-678",
        "Brasil",
        "SP",
        "São Paulo",
        "Jardim Primavera",
        "Rua Das Flores",
        "Apto 101",
        "123"
    );

    when(passportMapper.toDomain(clientRequest.passport())).thenReturn(passportExpected);
    when(addressMapper.toDomain(clientRequest.address())).thenReturn(addressExpected);
    when(textFormatter.format(clientRequest.completeName())).thenReturn("João Da Silva");
    when(dateConverter.convertToLocalDate(clientRequest.birthDate())).thenReturn(LocalDate.of(1990, 1, 1));

    // Act
    Client domain = clientMapper.toDomain(clientRequest);

    // Assert
    assertAll(
        () -> assertEquals(clientRequest.completeName(), domain.getCompleteName()),

        () -> assertEquals(clientRequest.cpf(), domain.getCpf()),
        () -> assertEquals(LocalDate.parse(clientRequest.birthDate(), DATE_PATTERN), domain.getBirthDate()),
        () -> assertEquals(clientRequest.phone(), domain.getPhone()),
        () -> assertEquals(clientRequest.email(), domain.getEmail()),

        () -> assertEquals(clientRequest.passport().number(), domain.getPassport().getNumber()),
        () -> assertEquals(LocalDate.parse(clientRequest.passport().emissionDate(), DATE_PATTERN), domain.getPassport().getEmissionDate()),
        () -> assertEquals(LocalDate.parse(clientRequest.passport().expirationDate(), DATE_PATTERN), domain.getPassport().getExpirationDate()),

        () -> assertEquals(clientRequest.address().zipCode(), domain.getAddress().getZipCode()),
        () -> assertEquals(clientRequest.address().country(), domain.getAddress().getCountry()),
        () -> assertEquals(clientRequest.address().state(), domain.getAddress().getState()),
        () -> assertEquals(clientRequest.address().city(), domain.getAddress().getCity()),
        () -> assertEquals(clientRequest.address().neighborhood(), domain.getAddress().getNeighborhood()),
        () -> assertEquals(clientRequest.address().street(), domain.getAddress().getStreet()),
        () -> assertEquals(clientRequest.address().complement(), domain.getAddress().getComplement()),
        () -> assertEquals(clientRequest.address().residentialNumber(), domain.getAddress().getResidentialNumber())
    );
  }

  @Test
  void ShouldMapClientDomainToDTO() {
    // Arrange
    Client clientDomain = createClientDomain();

    PassportDTO passportExpected = new PassportDTO(
        "AB123456",
        "01/06/2020",
        "01/06/2030"
    );

    AddressDTO addressExpected = new AddressDTO(
        "12345-678",
        "Brasil",
        "SP",
        "São Paulo",
        "Jardim Primavera",
        "Rua Das Flores",
        "Apto 101",
        "123"
    );

    when(passportMapper.toDTO(clientDomain.getPassport())).thenReturn(passportExpected);
    when(addressMapper.toDTO(clientDomain.getAddress())).thenReturn(addressExpected);
    when(dateConverter.convertToString(clientDomain.getBirthDate())).thenReturn("01/01/1990");

    // Act
    ClientResponseDTO response = clientMapper.toDTO(clientDomain);

    // Assert
    assertAll(
        () -> assertEquals(clientDomain.getId(), response.id()),
        () -> assertEquals(clientDomain.getCompleteName(), response.completeName()),
        () -> assertEquals(clientDomain.getCpf(), response.cpf()),
        () -> assertEquals(clientDomain.getBirthDate(), LocalDate.parse(response.birthDate(), DATE_PATTERN)),
        () -> assertEquals(clientDomain.getPhone(), response.phone()),
        () -> assertEquals(clientDomain.getEmail(), response.email()),

        () -> assertEquals(clientDomain.getPassport().getNumber(), response.passport().number()),
        () -> assertEquals(clientDomain.getPassport().getEmissionDate(), LocalDate.parse(response.passport().emissionDate(), DATE_PATTERN)),
        () -> assertEquals(clientDomain.getPassport().getExpirationDate(), LocalDate.parse(response.passport().expirationDate(), DATE_PATTERN)),

        () -> assertEquals(clientDomain.getAddress().getZipCode(), response.address().zipCode()),
        () -> assertEquals(clientDomain.getAddress().getCountry(), response.address().country()),
        () -> assertEquals(clientDomain.getAddress().getState(), response.address().state()),
        () -> assertEquals(clientDomain.getAddress().getCity(), response.address().city()),
        () -> assertEquals(clientDomain.getAddress().getNeighborhood(), response.address().neighborhood()),
        () -> assertEquals(clientDomain.getAddress().getStreet(), response.address().street()),
        () -> assertEquals(clientDomain.getAddress().getComplement(), response.address().complement()),
        () -> assertEquals(clientDomain.getAddress().getResidentialNumber(), response.address().residentialNumber())
    );
  }

  @Test
  void ShouldMapClientDomainToModel() {
    // Arrange
    Client clientDomain = createClientDomain();

    // Act
    ClientModel model = clientMapper.toModel(clientDomain);

    // Assert
    assertAll(
        () -> assertEquals(clientDomain.getId(), model.getId()),
        () -> assertEquals(clientDomain.getCompleteName(), model.getCompleteName()),
        () -> assertEquals(clientDomain.getCpf(), model.getCpf()),
        () -> assertEquals(clientDomain.getBirthDate(), model.getBirthDate()),
        () -> assertEquals(clientDomain.getPhone(), model.getPhone()),
        () -> assertEquals(clientDomain.getEmail(), model.getEmail()),

        () -> assertEquals(clientDomain.getPassport().getNumber(), model.getPassportNumber()),
        () -> assertEquals(clientDomain.getPassport().getEmissionDate(), model.getPassportEmissionDate()),
        () -> assertEquals(clientDomain.getPassport().getExpirationDate(), model.getPassportExpirationDate()),

        () -> assertEquals(clientDomain.getAddress().getZipCode(), model.getZipCode()),
        () -> assertEquals(clientDomain.getAddress().getCountry(), model.getCountry()),
        () -> assertEquals(clientDomain.getAddress().getState(), model.getState()),
        () -> assertEquals(clientDomain.getAddress().getCity(), model.getCity()),
        () -> assertEquals(clientDomain.getAddress().getNeighborhood(), model.getNeighborhood()),
        () -> assertEquals(clientDomain.getAddress().getStreet(), model.getStreet()),
        () -> assertEquals(clientDomain.getAddress().getComplement(), model.getComplement()),
        () -> assertEquals(clientDomain.getAddress().getResidentialNumber(), model.getResidentialNumber())
    );
  }

  @Test
  void ShouldMapClientModelToDomain() {
    // Arrange
    ClientModel clientModel = createClientModel();

    // Act
    Client domain = clientMapper.toDomain(clientModel);

    // Assert
    assertAll(
        () -> assertEquals(clientModel.getId(), domain.getId()),
        () -> assertEquals(clientModel.getCompleteName(), domain.getCompleteName()),
        () -> assertEquals(clientModel.getCpf(), domain.getCpf()),
        () -> assertEquals(clientModel.getBirthDate(), domain.getBirthDate()),
        () -> assertEquals(clientModel.getPhone(), domain.getPhone()),
        () -> assertEquals(clientModel.getEmail(), domain.getEmail()),

        () -> assertEquals(clientModel.getPassportNumber(), domain.getPassport().getNumber()),
        () -> assertEquals(clientModel.getPassportEmissionDate(), domain.getPassport().getEmissionDate()),
        () -> assertEquals(clientModel.getPassportExpirationDate(), domain.getPassport().getExpirationDate()),

        () -> assertEquals(clientModel.getZipCode(), domain.getAddress().getZipCode()),
        () -> assertEquals(clientModel.getCountry(), domain.getAddress().getCountry()),
        () -> assertEquals(clientModel.getState(), domain.getAddress().getState()),
        () -> assertEquals(clientModel.getCity(), domain.getAddress().getCity()),
        () -> assertEquals(clientModel.getNeighborhood(), domain.getAddress().getNeighborhood()),
        () -> assertEquals(clientModel.getStreet(), domain.getAddress().getStreet()),
        () -> assertEquals(clientModel.getComplement(), domain.getAddress().getComplement()),
        () -> assertEquals(clientModel.getResidentialNumber(), domain.getAddress().getResidentialNumber())
    );
  }

  @Test
  void ShouldMapClientRequestUpdateDTOToDomain() {
    // Arrange
    ClientRequestUpdateDTO dto = new ClientRequestUpdateDTO(
        "new name",
        "01/01/2000",
        "(12) 12345-6789",
        "newEmail.@example.com",
        new PassportDTO("AB123456", "01/01/2020", "01/01/2030"),
        new AddressDTO(
            "12345-678", "newCountry", "newState", "newCity",
            "newNeighborhood", "newStreet", "newComplement", "123")
    );

    Passport mappedPassport = new Passport("AB123456", LocalDate.of(2020, 1, 1), LocalDate.of(2030, 1, 1));
    Address mappedAddress = new Address("12345-678", "newCountry", "newState", "newCity", "newNeighborhood",
        "newStreet", "newComplement", "123"
    );

    when(passportMapper.toDomain(dto.passport())).thenReturn(mappedPassport);
    when(addressMapper.toDomain(dto.address())).thenReturn(mappedAddress);
    when(textFormatter.format(dto.name())).thenReturn("New Name");
    when(dateConverter.convertToLocalDate(dto.birthDate())).thenReturn(LocalDate.of(2000, 1, 1));

    // Act
    Client domain = clientMapper.toDomain(dto);

    // Assert
    assertAll(
        () -> assertEquals("New Name", domain.getCompleteName()),
        () -> assertEquals(LocalDate.of(2000, 1, 1), domain.getBirthDate()),
        () -> assertEquals("(12) 12345-6789", domain.getPhone()),
        () -> assertEquals("newEmail.@example.com", domain.getEmail()),

        () -> assertEquals(mappedPassport.getNumber(), domain.getPassport().getNumber()),
        () -> assertEquals(mappedPassport.getEmissionDate(), domain.getPassport().getEmissionDate()),
        () -> assertEquals(mappedPassport.getExpirationDate(), domain.getPassport().getExpirationDate()),

        () -> assertEquals(mappedAddress.getZipCode(), domain.getAddress().getZipCode()),
        () -> assertEquals(mappedAddress.getCountry(), domain.getAddress().getCountry()),
        () -> assertEquals(mappedAddress.getState(), domain.getAddress().getState()),
        () -> assertEquals(mappedAddress.getCity(), domain.getAddress().getCity()),
        () -> assertEquals(mappedAddress.getNeighborhood(), domain.getAddress().getNeighborhood()),
        () -> assertEquals(mappedAddress.getStreet(), domain.getAddress().getStreet()),
        () -> assertEquals(mappedAddress.getComplement(), domain.getAddress().getComplement()),
        () -> assertEquals(mappedAddress.getResidentialNumber(), domain.getAddress().getResidentialNumber())
        );
    verify(passportMapper).toDomain(dto.passport());
    verify(addressMapper).toDomain(dto.address());
  }
}
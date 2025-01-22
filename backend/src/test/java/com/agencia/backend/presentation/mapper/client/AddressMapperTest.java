package com.agencia.backend.presentation.mapper.client;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.agencia.backend.presentation.dto.address.AddressDTO;
import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.presentation.mapper.client.implementation.AddressMapperImp;
import com.agencia.backend.presentation.utils.textFormatter.TextFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AddressMapperTest {

  @InjectMocks
  private AddressMapperImp addressMapper;

  @Mock
  private TextFormatter textFormatter;

  private Address createAddressDomain() {
    return new Address(
        "12345-678",
        "Brasil",
        "SP",
        "São Paulo",
        "Jardim Primavera",
        "Rua Das Flores",
        "Apto 101",
        "123"
    );
  }

  private AddressDTO createAddressDTO() {
    return new AddressDTO(
        "12345-678",
        "Brasil",
        "Sp",
        "São Paulo",
        "Jardim Primavera",
        "Rua Das Flores",
        "Apto 101",
        "123"
    );
  }

  @Test
  void ShouldMapAddressDTOToDomain() {
    // Arrange
    AddressDTO dto = createAddressDTO();

    when(textFormatter.format(dto.country())).thenReturn("Brasil");
    when(textFormatter.format(dto.city())).thenReturn("São Paulo");
    when(textFormatter.format(dto.neighborhood())).thenReturn("Jardim Primavera");
    when(textFormatter.format(dto.street())).thenReturn("Rua Das Flores");

    // Act
    Address domain = addressMapper.toDomain(dto);

    // Assert
    assertAll(
        () -> assertEquals(dto.zipCode(), domain.getZipCode()),
        () -> assertEquals(dto.country(), domain.getCountry()),
        () -> assertEquals(dto.state(), domain.getState()),
        () -> assertEquals(dto.city(), domain.getCity()),
        () -> assertEquals(dto.neighborhood(), domain.getNeighborhood()),
        () -> assertEquals(dto.street(), domain.getStreet()),
        () -> assertEquals(dto.complement(), domain.getComplement()),
        () -> assertEquals(dto.residentialNumber(), domain.getResidentialNumber())
    );
  }

  @Test
  void ShouldMapAddressDomainToDTO() {
    // Arrange
    Address domain = createAddressDomain();

    // Act
    AddressDTO dto = addressMapper.toDTO(domain);

    // Assert
    assertAll(
        () -> assertEquals(domain.getZipCode(), dto.zipCode()),
        () -> assertEquals(domain.getCountry(), dto.country()),
        () -> assertEquals(domain.getState(), dto.state()),
        () -> assertEquals(domain.getCity(), dto.city()),
        () -> assertEquals(domain.getNeighborhood(), dto.neighborhood()),
        () -> assertEquals(domain.getStreet(), dto.street()),
        () -> assertEquals(domain.getComplement(), dto.complement()),
        () -> assertEquals(domain.getResidentialNumber(), dto.residentialNumber())
    );
  }

  @Test
  void ShouldMapNullAddressDtoToDomain() {
    // Arrange
    AddressDTO dto = null;

    // Act
    Address domain = addressMapper.toDomain(dto);

    // Assert
    assertAll(
        () -> assertEquals(null, domain.getZipCode()),
        () -> assertEquals(null, domain.getCountry()),
        () -> assertEquals(null, domain.getState()),
        () -> assertEquals(null, domain.getCity()),
        () -> assertEquals(null, domain.getNeighborhood()),
        () -> assertEquals(null, domain.getStreet()),
        () -> assertEquals(null, domain.getComplement()),
        () -> assertEquals(null, domain.getResidentialNumber())
    );
  }

}
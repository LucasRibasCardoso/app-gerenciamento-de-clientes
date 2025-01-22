package com.agencia.backend.presentation.mapper.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.agencia.backend.presentation.dto.passport.PassportDTO;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.presentation.mapper.client.implementation.PassportMapperImp;
import com.agencia.backend.presentation.utils.dateConverter.DateConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PassportMapperTest {

  private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");

  @InjectMocks
  private PassportMapperImp passportMapper;
  @Mock
  private DateConverter dateConverter;

  private PassportDTO createPassportDTO() {
    return new PassportDTO("AB123456", "01/01/2020", "01/01/2030");
  }

  private Passport createPassportDomain() {
    return new Passport("AB123456", LocalDate.of(2020, 1, 1), LocalDate.of(2030, 1, 1));
  }

  @Test
  void ShouldMapPassportDtoToDomain() {
    // Arrange
    PassportDTO dto = createPassportDTO();

    when(dateConverter.convertToLocalDate(dto.emissionDate())).thenReturn(LocalDate.of(2020, 1, 1));
    when(dateConverter.convertToLocalDate(dto.expirationDate())).thenReturn(LocalDate.of(2030, 1, 1));

    // Act
    Passport domain = passportMapper.toDomain(dto);

    // Assert
    assertAll(
        () -> assertEquals(dto.number(), domain.getNumber()),
        () -> assertEquals(LocalDate.parse(dto.emissionDate(), DATE_PATTERN), domain.getEmissionDate() ),
        () -> assertEquals(LocalDate.parse(dto.expirationDate(), DATE_PATTERN), domain.getExpirationDate())
    );
  }

  @Test
  void ShouldMapPassportNullToDomain() {
    // Arrange
    PassportDTO dto = null;

    // Act
    Passport domain = passportMapper.toDomain(dto);

    // Assert
    assertAll(
        () -> assertNull(domain.getNumber()),
        () -> assertNull(domain.getEmissionDate()),
        () -> assertNull(domain.getExpirationDate())
    );
  }

  @Test
  void ShouldMapPassportDomainToDTO() {
    // Arrange
    Passport domain = createPassportDomain();

    when(dateConverter.convertToString(domain.getEmissionDate())).thenReturn("01/01/2020");
    when(dateConverter.convertToString(domain.getExpirationDate())).thenReturn("01/01/2030");

    // Act
    PassportDTO dto = passportMapper.toDTO(domain);

    // Assert
    assertAll(
        () -> assertEquals(domain.getNumber(), dto.number()),
        () -> assertEquals(domain.getEmissionDate(), LocalDate.parse(dto.emissionDate(), DATE_PATTERN)),
        () -> assertEquals(domain.getExpirationDate(), LocalDate.parse(dto.expirationDate(), DATE_PATTERN))
    );
  }

}
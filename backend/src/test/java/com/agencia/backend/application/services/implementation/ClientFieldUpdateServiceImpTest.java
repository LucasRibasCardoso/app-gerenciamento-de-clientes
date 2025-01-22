package com.agencia.backend.application.services.implementation;

import static org.junit.jupiter.api.Assertions.*;

import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ClientFieldUpdateServiceImpTest {

  private ClientFieldUpdateServiceImp clientFieldUpdateService =  new ClientFieldUpdateServiceImp();

  @Test
  void updateName_ShouldReturnNewNameWhenNotNull() {
    assertEquals("New Name", clientFieldUpdateService.updateName("Old Name", "New Name"));
  }

  @Test
  void updateName_ShouldReturnExistingNameWhenNewNameIsNull() {
    assertEquals("Old Name", clientFieldUpdateService.updateName("Old Name", null));
  }

  @Test
  void updateBirthDate_ShouldReturnNewBirthDateWhenNotNull() {
    assertEquals("2000-01-01", clientFieldUpdateService.updateBirthDate("1990-01-01", "2000-01-01"));
  }

  @Test
  void updateBirthDate_ShouldReturnExistingBirthDateWhenNewBirthDateIsNull() {
    assertEquals("1990-01-01", clientFieldUpdateService.updateBirthDate("1990-01-01", null));
  }

  @Test
  void updatePhone_ShouldReturnNewPhoneWhenNotNull() {
    assertEquals("1234567890", clientFieldUpdateService.updatePhone("0987654321", "1234567890"));
  }

  @Test
  void updatePhone_ShouldReturnExistingPhoneWhenNewPhoneIsNull() {
    assertEquals("0987654321", clientFieldUpdateService.updatePhone("0987654321", null));
  }

  @Test
  void updateEmail_ShouldReturnNewEmailWhenNotNull() {
    assertEquals("new@example.com", clientFieldUpdateService.updateEmail("old@example.com", "new@example.com"));
  }

  @Test
  void updateEmail_ShouldReturnExistingEmailWhenNewEmailIsNull() {
    assertEquals("old@example.com", clientFieldUpdateService.updateEmail("old@example.com", null));
  }

  @Test
  void updatePassport_ShouldReturnUpdatedPassport() {
    Passport existingPassport = new Passport("A123456", LocalDate.of(2020, 1, 1), LocalDate.of(2030, 1, 1));
    Passport newPassport = new Passport(null, LocalDate.of(2025, 1, 1), null);

    Passport updatedPassport = clientFieldUpdateService.updatePassport(existingPassport, newPassport);

    assertEquals("A123456", updatedPassport.getNumber());
    assertEquals(LocalDate.of(2025, 1, 1), updatedPassport.getEmissionDate());
    assertEquals( LocalDate.of(2030, 1, 1), updatedPassport.getExpirationDate());
  }

  @Test
  void updateAddress_ShouldReturnUpdatedAddress() {
    Address existingAddress = new Address(
        "12345", "Country1", "State1", "City1", "Neighborhood1", "Street1", "Complement1", "100"
    );

    Address newAddress = new Address(null, "Country2", null, "City2", null, null, "Complement2", null);

    Address updatedAddress = clientFieldUpdateService.updateAddress(existingAddress, newAddress);

    assertEquals("12345", updatedAddress.getZipCode());
    assertEquals("Country2", updatedAddress.getCountry());
    assertEquals("State1", updatedAddress.getState());
    assertEquals("City2", updatedAddress.getCity());
    assertEquals("Neighborhood1", updatedAddress.getNeighborhood());
    assertEquals("Street1", updatedAddress.getStreet());
    assertEquals("Complement2", updatedAddress.getComplement());
    assertEquals("100", updatedAddress.getResidentialNumber());
  }

  @Test
  void updateClient_ShouldReturnUpdatedClient() {
    Client existingClient = new Client(
        1L,
        "Old Name",
        "123.456.789-01",
        LocalDate.of(1990, 1, 1),
        "(12) 12345-6789",
        "old@example.com",
        new Passport("A123456",  LocalDate.of(2020, 1, 1),  LocalDate.of(2030, 1, 1)),
        new Address("12345", "Country1", "State1", "City1", "Neighborhood1", "Street1", "Complement1", "100")
    );

    Client clientRequest = new Client(
        null,
        "New Name",
        null,
        LocalDate.of(2000, 1, 1),
        null,
        "new@example.com",
        new Passport(null,  LocalDate.of(2021, 1, 1), null),
        new Address(null, "Country2", null, "City2", null, null, "Complement2", null)
    );

    Client updatedClient = clientFieldUpdateService.updateClient(existingClient, clientRequest);

    assertAll(
        () -> assertEquals(existingClient.getId(), updatedClient.getId()),
        () -> assertEquals("New Name", updatedClient.getCompleteName()),
        () -> assertEquals(existingClient.getCpf(), updatedClient.getCpf()),
        () -> assertEquals(LocalDate.of(2000, 1, 1), updatedClient.getBirthDate()),
        () -> assertEquals(existingClient.getPhone(), updatedClient.getPhone()),
        () -> assertEquals("new@example.com", updatedClient.getEmail()),

        () -> assertEquals(existingClient.getPassport().getNumber(), updatedClient.getPassport().getNumber()),
        () -> assertEquals(LocalDate.of(2021, 1, 1), updatedClient.getPassport().getEmissionDate()),
        () -> assertEquals(existingClient.getPassport().getExpirationDate(), updatedClient.getPassport().getExpirationDate()),

        () -> assertEquals(existingClient.getAddress().getZipCode(), updatedClient.getAddress().getZipCode()),
        () -> assertEquals("Country2", updatedClient.getAddress().getCountry()),
        () -> assertEquals(existingClient.getAddress().getState(), updatedClient.getAddress().getState()),
        () -> assertEquals("City2", updatedClient.getAddress().getCity()),
        () -> assertEquals(existingClient.getAddress().getNeighborhood(), updatedClient.getAddress().getNeighborhood()),
        () -> assertEquals(existingClient.getAddress().getStreet(), updatedClient.getAddress().getStreet()),
        () -> assertEquals("Complement2", updatedClient.getAddress().getComplement()),
        () -> assertEquals(existingClient.getAddress().getResidentialNumber(), updatedClient.getAddress().getResidentialNumber())
    );
  }
}
package com.agencia.backend.application.services.implementation;

import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.application.services.ClientFieldUpdateService;

public class ClientFieldUpdateServiceImp implements ClientFieldUpdateService {

  @Override
  public String updateName(String existingName, String newName) {
    return newName != null ? newName : existingName;
  }

  @Override
  public String updateBirthDate(String existingBirthDate, String newBirthDate) {
    return newBirthDate != null ? newBirthDate : existingBirthDate;
  }

  @Override
  public String updatePhone(String existingPhone, String newPhone) {
    return newPhone != null ? newPhone : existingPhone;
  }

  @Override
  public String updateEmail(String existingEmail, String newEmail) {
    return newEmail != null ? newEmail : existingEmail;
  }

  @Override
  public Passport updatePassport(Passport existingPassport, Passport newPassport) {
    return new Passport(
        newPassport.getNumber() != null ? newPassport.getNumber() : existingPassport.getNumber(),
        newPassport.getEmissionDate() != null ? newPassport.getEmissionDate() : existingPassport.getEmissionDate(),
        newPassport.getExpirationDate() != null ? newPassport.getExpirationDate() : existingPassport.getExpirationDate()
    );
  }

  @Override
  public Address updateAddress(Address existingAddress, Address newAddress) {
    return new Address(
        newAddress.getZipCode() != null ? newAddress.getZipCode() : existingAddress.getZipCode(),
        newAddress.getCountry() != null ? newAddress.getCountry() : existingAddress.getCountry(),
        newAddress.getState() != null ? newAddress.getState() : existingAddress.getState(),
        newAddress.getCity() != null ? newAddress.getCity() : existingAddress.getCity(),
        newAddress.getNeighborhood() != null ? newAddress.getNeighborhood() : existingAddress.getNeighborhood(),
        newAddress.getStreet() != null ? newAddress.getStreet() : existingAddress.getStreet(),
        newAddress.getComplement() != null ? newAddress.getComplement() : existingAddress.getComplement(),
        newAddress.getResidentialNumber() != null ? newAddress.getResidentialNumber() : existingAddress.getResidentialNumber()
    );
  }

  @Override
  public Client updateClient(Client existingClient, Client clientRequest) {
    return new Client(
        existingClient.getId(), // ID não pode ser alterado
        updateName(existingClient.getCompleteName(), clientRequest.getCompleteName()),
        existingClient.getCpf(), // CPF não pode ser alterado
        clientRequest.getBirthDate() != null ? clientRequest.getBirthDate() : existingClient.getBirthDate(),
        updatePhone(existingClient.getPhone(), clientRequest.getPhone()),
        updateEmail(existingClient.getEmail(), clientRequest.getEmail()),
        updatePassport(existingClient.getPassport(), clientRequest.getPassport()),
        updateAddress(existingClient.getAddress(), clientRequest.getAddress())
    );
  }

}

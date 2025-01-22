package com.agencia.backend.application.services;

import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;

public interface ClientFieldUpdateService {
  String updateName(String existingName, String newName);
  String updateBirthDate(String existingBirthDate, String newBirthDate);
  String updatePhone(String existingPhone, String newPhone);
  String updateEmail(String existingEmail, String newEmail);
  Passport updatePassport(Passport existingPassport, Passport newPassport);
  Address updateAddress(Address existingAddress, Address newAddress);
  Client updateClient(Client existingClient, Client clientRequest);
}

package com.agencia.backend.domain.entity;

import com.agencia.backend.domain.exceptions.client.InvalidBirthDateClientException;
import java.time.LocalDate;

public class Client {

  private final Long id;
  private final String completeName;
  private final String cpf;
  private final LocalDate birthDate;
  private final String phone;
  private final String email;
  private final Passport passport;
  private final Address address;


  public Client(
      Long id,
      String completeName,
      String cpf,
      LocalDate birthDate,
      String phone,
      String email,
      Passport passport,
      Address address
  ) {
    this.id = id;
    this.completeName = completeName;
    this.cpf = cpf;
    this.birthDate = birthDate;
    this.phone = phone;
    this.email = email;
    this.passport = passport;
    this.address = address;

    validateBirthDate();
  }

  public void validateBirthDate() {
    if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
      throw new InvalidBirthDateClientException("A data de nascimento deve ser anterior à data atual");
    }
  }

  public Long getId() {
    return id;
  }

  public String getCompleteName() {
    return completeName;
  }

  public String getCpf() {
    return cpf;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public String getPhone() {
    return phone;
  }

  public String getEmail() {
    return email;
  }

  public Passport getPassport() {
    return passport;
  }

  public Address getAddress() {
    return address;
  }

}

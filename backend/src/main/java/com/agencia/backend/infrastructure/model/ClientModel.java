package com.agencia.backend.infrastructure.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_clients")
@Getter
@Setter
public class ClientModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Cliente
  @Column(nullable = false)
  private String completeName;

  @Column(unique = true, nullable = false)
  private String cpf;

  @Column(name = "birthDate", nullable = false)
  private LocalDate birthDate;

  private String phone;

  @Column(unique = true)
  private String email;

  // Passaporte
  @Column(unique = true)
  private String passportNumber;

  @Column(name = "passportEmissionDate")
  private LocalDate passportEmissionDate;

  @Column(name = "passportExpirationDate")
  private LocalDate passportExpirationDate;

  // Endereço
  private String zipCode;
  private String country;
  private String state;
  private String city;
  private String neighborhood;
  private String street;
  private String complement;
  private String residentialNumber;

  public ClientModel() {
  }

  public ClientModel(
      Long id,
      String completeName,
      String cpf,
      LocalDate birthDate,
      String phone,
      String email,
      String passportNumber,
      LocalDate passportEmissionDate,
      LocalDate passportExpirationDate,
      String zipCode,
      String country,
      String state,
      String city,
      String neighborhood,
      String street,
      String complement,
      String residentialNumber
  ) {
    this.id = id;
    this.completeName = completeName;
    this.cpf = cpf;
    this.birthDate = birthDate;
    this.phone = phone;
    this.email = email;
    this.passportNumber = passportNumber;
    this.passportEmissionDate = passportEmissionDate;
    this.passportExpirationDate = passportExpirationDate;
    this.zipCode = zipCode;
    this.country = country;
    this.state = state;
    this.city = city;
    this.neighborhood = neighborhood;
    this.street = street;
    this.complement = complement;
    this.residentialNumber = residentialNumber;
  }

}
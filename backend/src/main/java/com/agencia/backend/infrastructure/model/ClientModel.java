package com.agencia.backend.infrastructure.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
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

  @Column(nullable = false)
  private String completeName;

  @Column(nullable = false)
  LocalDate birthDate;

  @Column(unique = true)
  private String email;

  private LocalDate passportEmissionDate;
  private LocalDate passportExpirationDate;

  // Campos com criptografia e hash
  @Column(name = "cpf_encrypted", unique = true, nullable = false)
  private String encryptedCpf;

  @Column(name = "cpf_hashed", unique = true, nullable = false)
  private String hashedCpf;

  @Column(name = "passport_number_encrypted", unique = true)
  private String encryptedPassportNumber;

  @Column(name = "passport_number_hashed", unique = true)
  private String hashedPassportNumber;

  @Column(name = "phone_encrypted")
  private String encryptedPhone;

  @Column(name = "zip_code_encrypted")
  private String encryptedZipCode;

  @Column(name = "country_encrypted")
  private String encryptedCountry;

  @Column(name = "state_encrypted")
  private String encryptedState;

  @Column(name = "city_encrypted")
  private String encryptedCity;

  @Column(name = "neighborhood_encrypted")
  private String encryptedNeighborhood;

  @Column(name = "street_encrypted")
  private String encryptedStreet;

  @Column(name = "complement_encrypted")
  private String encryptedComplement;

  @Column(name = "residencial_number_encrypted")
  private String encryptedResidencialNumber;

  // Campos não persistentes
  @Transient
  private String rawCpf;

  @Transient
  private String rawPassportNumber;

  @Transient
  private String rawPhone;

  @Transient
  private String rawZipCode;

  @Transient
  private String rawCountry;

  @Transient
  private String rawState;

  @Transient
  private String rawCity;

  @Transient
  private String rawNeighborhood;

  @Transient
  private String rawStreet;

  @Transient
  private String rawComplement;

  @Transient
  private String rawResidentialNumber;

  public ClientModel() {
  }

  public ClientModel(
      Long id,
      String completeName,
      String rawCpf,
      LocalDate birthDate,
      String rawPhone,
      String email,
      String rawPassportNumber,
      LocalDate passportEmissionDate,
      LocalDate passportExpirationDate,
      String rawZipCode,
      String rawCountry,
      String rawState,
      String rawCity,
      String rawNeighborhood,
      String rawStreet,
      String rawComplement,
      String rawResidentialNumber
  ) {
    this.id = id;
    this.completeName = completeName;
    this.rawCpf = rawCpf;
    this.birthDate = birthDate;
    this.rawPhone = rawPhone;
    this.email = email;
    this.rawPassportNumber = rawPassportNumber;
    this.passportEmissionDate = passportEmissionDate;
    this.passportExpirationDate = passportExpirationDate;
    this.rawZipCode = rawZipCode;
    this.rawCountry = rawCountry;
    this.rawState = rawState;
    this.rawCity = rawCity;
    this.rawNeighborhood = rawNeighborhood;
    this.rawStreet = rawStreet;
    this.rawComplement = rawComplement;
    this.rawResidentialNumber = rawResidentialNumber;
  }

}
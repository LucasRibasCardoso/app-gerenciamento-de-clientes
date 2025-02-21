package com.agencia.backend.infrastructure.model;

import com.agencia.backend.infrastructure.configuration.encryption.CryptoService;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Table(name = "tb_clients")
@Getter
public class ClientModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String completeName;

  LocalDate birthDate;
  private String phone;

  @Column(unique = true)
  private String email;

  @Column(unique = true)
  private String passportNumber;

  private LocalDate passportEmissionDate;
  private LocalDate passportExpirationDate;
  private String zipCode;
  private String country;
  private String state;
  private String city;
  private String neighborhood;
  private String street;
  private String complement;
  private String residentialNumber;

  // Campos criptografados e hasheados
  @Column(name ="cpf_encrypted" , unique = true, nullable = false)
  private String encryptedCpf;

  @Column(name = "cpf_hashed", unique = true, nullable = false)
  private String hashedCpf;


  // Campos não persistentes
  @Transient
  private String rawCpf;



  public ClientModel() {}

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
      String residentialNumber) {
    this.id = id;
    this.completeName = completeName;
    this.rawCpf = cpf;
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

  @PrePersist
  @PreUpdate
  public void encryptCpf() {
    // Criptografa para armazenamento reversível
    this.encryptedCpf = CryptoService.encrypt(this.rawCpf);

    // Gera hash para comparações
    this.hashedCpf = CryptoService.hash(this.rawCpf);
  }

  @PostLoad
  public void decryptCpf() {
    // Descriptografa apenas o campo criptografado para uso interno
    this.rawCpf = CryptoService.decrypt(this.encryptedCpf);
  }
}

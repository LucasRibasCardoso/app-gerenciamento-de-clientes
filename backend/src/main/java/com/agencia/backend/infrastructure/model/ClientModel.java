package com.agencia.backend.infrastructure.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_clients")
@Getter
@Setter
@NoArgsConstructor
public class ClientModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Dados principais do cliente
  @Column(nullable = false)
  private String completeName;

  @Column(unique = true, nullable = false)
  private String cpf;

  private LocalDate birthDate;

  private String phone;

  @Column(unique = true)
  private String email;

  // Relacionamentos
  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "passport_id")
  private PassportModel passport;

  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "address_id")
  private AddressModel address;

  public ClientModel(
      Long id,
      String completeName,
      String cpf,
      LocalDate birthDate,
      String phone,
      String email,
      PassportModel passport,
      AddressModel address
  ) {
    this.id = id;
    this.completeName = completeName;
    this.cpf = cpf;
    this.birthDate = birthDate;
    this.phone = phone;
    this.email = email;
    this.passport = passport;
    this.address = address;
  }

}
package com.agencia.backend.infrastructure.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Dados principais do cliente
    @Column(nullable = false)
    private String completeName;

    @Column(unique = true, nullable = false)
    private String cpf;

    private LocalDate birthDate;

    private String phone;

    @Column(unique = true)
    private String email;

    // Modifique os relacionamentos para que aceitem null
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "passport_id", nullable = true)
    private PassportModel passport;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id", nullable = true)
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

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
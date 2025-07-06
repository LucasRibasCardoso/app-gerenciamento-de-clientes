package com.agencia.backend.infrastructure.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_passports")
@Getter
@Setter
@NoArgsConstructor
public class PassportModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String passportNumber;

    private LocalDate emissionDate;

    private LocalDate expirationDate;

    public PassportModel(Long id, String passportNumber, LocalDate emissionDate, LocalDate expirationDate) {
        this.id = id;
        this.passportNumber = passportNumber;
        this.emissionDate = emissionDate;
        this.expirationDate = expirationDate;
    }
}

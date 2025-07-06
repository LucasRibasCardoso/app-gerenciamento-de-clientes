package com.agencia.backend.domain.entity;

import com.agencia.backend.domain.exceptions.client.InvalidPassportDatesException;

import java.time.LocalDate;

public class Passport {

    private Long id;
    private String number;
    private LocalDate emissionDate;
    private LocalDate expirationDate;

    public Passport(String number, LocalDate emissionDate, LocalDate expirationDate) {
        this(null, number, emissionDate, expirationDate);

        validateDates();
    }

    public Passport(Long id, String number, LocalDate emissionDate, LocalDate expirationDate) {
        this.id = id;
        this.number = number;
        this.emissionDate = emissionDate;
        this.expirationDate = expirationDate;

        validateDates();
    }

    public void validateDates() {
        if (emissionDate != null && expirationDate != null) {
            if (emissionDate.isAfter(expirationDate)) {
                throw new InvalidPassportDatesException(
                        "A data de emissão não pode ser posterior à data de validade.");
            }
        }

        if (emissionDate != null && emissionDate.isAfter(LocalDate.now())) {
            throw new InvalidPassportDatesException("A data de emissão não pode ser posterior à data atual.");
        }

        if (expirationDate != null && expirationDate.isBefore(LocalDate.now())) {
            throw new InvalidPassportDatesException("A data de validade não pode ser anterior à data atual. O passaporte está vencido.");
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDate getEmissionDate() {
        return emissionDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public String getNumber() {
        return number;
    }

    public void setEmissionDate(LocalDate emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}

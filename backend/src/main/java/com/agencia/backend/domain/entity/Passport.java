package com.agencia.backend.domain.entity;

import com.agencia.backend.domain.exceptions.client.InvalidPassportDatesException;
import java.time.LocalDate;

public class Passport {

  private final String number;
  private final LocalDate emissionDate;
  private final LocalDate expirationDate;

  public Passport(String number, LocalDate emissionDate, LocalDate expirationDate) {
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

  public LocalDate getEmissionDate() {
    return emissionDate;
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public String getNumber() {
    return number;
  }

}

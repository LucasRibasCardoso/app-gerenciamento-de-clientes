package com.agencia.backend.presentation.utils.dateConverter;

import com.agencia.backend.domain.exceptions.global.InvalidDateFormatException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DefaultDateConverter implements DateConverter {

  private final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yyyy");


  @Override
  public LocalDate convertToLocalDate(String date) {
    try {
      return date != null ? LocalDate.parse(date, DATE_PATTERN) : null;
    } catch (DateTimeParseException e) {
      throw new InvalidDateFormatException("Formato inválido da data, utilize dd/MM/yyyy");
    }
  }


  public String convertToString(LocalDate date) {
    return date != null ? date.format(DATE_PATTERN) : null;
  }
}

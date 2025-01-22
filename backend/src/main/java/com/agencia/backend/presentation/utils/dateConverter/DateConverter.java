package com.agencia.backend.presentation.utils.dateConverter;

import java.time.LocalDate;

public interface DateConverter {
  LocalDate convertToLocalDate(String date);
  String convertToString(LocalDate date);
}

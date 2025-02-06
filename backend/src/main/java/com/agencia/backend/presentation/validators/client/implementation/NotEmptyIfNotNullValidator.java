package com.agencia.backend.presentation.validators.client.implementation;


import com.agencia.backend.presentation.validators.client.NotEmptyIfNotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NotEmptyIfNotNullValidator implements ConstraintValidator<NotEmptyIfNotNull, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    // Permite que o campo seja nulo
    if (value == null) {
      return true;
    }
    // Valida que o campo não seja uma string vazia ou composta apenas por espaços em branco
    return !value.trim().isEmpty();
  }
}

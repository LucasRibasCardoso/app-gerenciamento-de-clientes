package com.agencia.backend.presentation.validators.client;

public interface UrlParametersValidator {
  void validateID(Long id);
  void validateOrderBy(String orderBy);
  void validateSortOrder(String sortOrder);
}

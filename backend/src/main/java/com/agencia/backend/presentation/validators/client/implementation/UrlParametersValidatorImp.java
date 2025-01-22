package com.agencia.backend.presentation.validators.client.implementation;

import com.agencia.backend.domain.entity.enuns.OrderByField;
import com.agencia.backend.domain.entity.enuns.SortOrder;
import com.agencia.backend.domain.exceptions.client.InvalidClientIdException;
import com.agencia.backend.domain.exceptions.client.InvalidSortingParameterException;
import com.agencia.backend.presentation.validators.client.UrlParametersValidator;

public class UrlParametersValidatorImp implements UrlParametersValidator {

  @Override
  public void validateID(Long id) {
    if (id == null || id <= 0) {
      throw new InvalidClientIdException("O ID deve ser um valor positivo.");
    }
  }

  @Override
  public void validateOrderBy(String orderBy) {
    if (orderBy == null || !OrderByField.isValid(orderBy)) {
      throw new InvalidSortingParameterException("Parâmetro de ordenação inválido. Você pode ordenar os resultados pelo nome do cliente ou pelo seu ID.");
    }
  }

  @Override
  public void validateSortOrder(String sortOrder) {
    if (sortOrder == null || !SortOrder.isValid(sortOrder)) {
      throw new InvalidSortingParameterException("Direção de ordenação inválida. Você pode ordenar os resultados em ordem crescente ou decrescente.");
    }
  }

}

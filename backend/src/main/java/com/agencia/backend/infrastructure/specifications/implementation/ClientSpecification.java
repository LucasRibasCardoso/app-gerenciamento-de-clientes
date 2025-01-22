package com.agencia.backend.infrastructure.specifications.implementation;

import com.agencia.backend.infrastructure.model.ClientModel;
import com.agencia.backend.infrastructure.specifications.SpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class ClientSpecification implements SpecificationBuilder<ClientModel> {

  @Override
  public Specification<ClientModel> build(String search) {
    return Specification.where(nameContains(search))
        .or(emailContains(search))
        .or(cpfContains(search))
        .or(phoneContains(search));
  }

  private Specification<ClientModel> fieldContains(String fieldName, String search) {
    return (root, criteriaQuery, builder) -> {
      if (!StringUtils.hasText(search)) {
        return builder.conjunction();
      }
      return builder.like(root.get(fieldName), "%" + search + "%");
    };
  }

  private Specification<ClientModel> nameContains(String search) {
    return fieldContains("completeName", search);
  }

  private Specification<ClientModel> cpfContains(String search) {
    return fieldContains("cpf", search);
  }

  private Specification<ClientModel> emailContains(String search) {
    return fieldContains("email", search);
  }

  private Specification<ClientModel> phoneContains(String search) {
    return fieldContains("phone", search);
  }
}

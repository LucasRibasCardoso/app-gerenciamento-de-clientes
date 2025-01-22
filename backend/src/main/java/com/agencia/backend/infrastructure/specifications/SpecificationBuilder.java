package com.agencia.backend.infrastructure.specifications;

import org.springframework.data.jpa.domain.Specification;

public interface SpecificationBuilder<T> {
  Specification<T> build(String search);
}

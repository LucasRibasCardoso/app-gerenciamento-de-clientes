package com.agencia.backend.infrastructure.repository.client.implementation;

import com.agencia.backend.domain.repository.PassportRepository;
import com.agencia.backend.infrastructure.repository.client.PassportJpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PassportRepositoryImp implements PassportRepository {

  private final PassportJpaRepository jpaRepository;

  public PassportRepositoryImp(PassportJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  @Override
  public boolean existsByPassportNumber(String passportNumber) {
    return jpaRepository.existsByPassportNumber(passportNumber);
  }
}

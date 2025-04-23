package com.agencia.backend.domain.repository;


public interface PassportRepository {
  boolean existsByPassportNumber(String passportNumber);
}

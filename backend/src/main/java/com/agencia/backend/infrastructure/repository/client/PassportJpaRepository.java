package com.agencia.backend.infrastructure.repository.client;

import com.agencia.backend.infrastructure.model.PassportModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassportJpaRepository extends JpaRepository<PassportModel, Long> {
    boolean existsByPassportNumber(String passportNumber);
}

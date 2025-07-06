package com.agencia.backend.infrastructure.repository.client;

import com.agencia.backend.infrastructure.model.ClientModel;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientJpaRepository extends JpaRepository<ClientModel, Long>, JpaSpecificationExecutor<ClientModel> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    // Busca o total de clientes cadastrados
    long count();

    // Busca a quantidade de clientes cadastrados nos últimos 30 dias (data informada)
    long countByCreatedAtGreaterThanEqual(LocalDateTime date);

    // Busca a quantidade de clientes com passaporte
    long countByPassportPassportNumberIsNotNull();

    // Busca a quantidade de clientes sem passaporte
    long countByPassportPassportNumberIsNull();

    // Para o clientsThatNeedToRenewPassport - expiração menor que 1 ano a partir da data atual
    List<ClientModel> findByPassportPassportNumberIsNotNullAndPassportExpirationDateLessThan(LocalDate oneYearFromNow);
}

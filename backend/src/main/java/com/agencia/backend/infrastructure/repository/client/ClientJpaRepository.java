package com.agencia.backend.infrastructure.repository.client;

import com.agencia.backend.infrastructure.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClientJpaRepository extends JpaRepository<ClientModel, Long>, JpaSpecificationExecutor<ClientModel> {

  boolean existsByCpf(String cpf);

  boolean existsByPassportNumber(String email);

  boolean existsByEmail(String email);
}

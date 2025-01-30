package com.agencia.backend.domain.repository;

import com.agencia.backend.domain.entity.Client;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ClientRepository {

  Client save(Client client);

  Page<Client> findAll(String search, String orderBy, String sortOrder, int page, int size);

  Optional<Client> findById(Long id);

  boolean existsByCpf(String cpf);

  boolean existsByPassportNumber(String passportNumber);

  boolean existsByEmail(String email);

  boolean existsById(Long id);

  void deleteById(Long id);

}

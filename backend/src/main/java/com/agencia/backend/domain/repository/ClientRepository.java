package com.agencia.backend.domain.repository;

import com.agencia.backend.domain.entity.Client;
import java.util.List;
import java.util.Optional;

public interface ClientRepository {

  Client save(Client client);

  List<Client> findAll(String search, String orderBy, String sortOrder, int page, int size);

  Optional<Client> findById(Long id);

  boolean existsByCpf(String cpf);

  boolean existsByPassportNumber(String passportNumber);

  boolean existsByEmail(String email);

  boolean existsById(Long id);

  void deleteById(Long id);

}

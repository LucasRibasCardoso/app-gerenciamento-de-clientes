package com.agencia.backend.domain.repository;

import com.agencia.backend.domain.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
  Optional<User> findByUsername(String userName);
  List<User> findAll();
  User save(User user);
  void deleteById(UUID id);
  boolean existsById(UUID id);
  boolean existsByUsername(String username);
}

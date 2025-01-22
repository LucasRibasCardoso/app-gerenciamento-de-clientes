package com.agencia.backend.infrastructure.repository.user;

import com.agencia.backend.infrastructure.model.UserModel;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserModel, UUID> {
  Optional<UserModel> findByUserName(String userName);
  boolean existsByUserName(String userName);
}

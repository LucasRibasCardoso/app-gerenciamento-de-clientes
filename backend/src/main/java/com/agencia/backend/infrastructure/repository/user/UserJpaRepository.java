package com.agencia.backend.infrastructure.repository.user;

import com.agencia.backend.infrastructure.model.UserModel;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUsername(String userName);

    Optional<UserModel> findById(UUID id);

    boolean existsByUsername(String userName);
}

package com.agencia.backend.infrastructure.repository.user.implementation;

import com.agencia.backend.infrastructure.model.UserModel;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.repository.UserRepository;
import com.agencia.backend.infrastructure.repository.user.UserJpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class UserJpaRepositoryImp implements UserRepository {

  private final UserJpaRepository userJpaRepository;
  private final UserMapper userMapper;

  public UserJpaRepositoryImp(UserJpaRepository userJpaRepository, UserMapper userMapper) {
    this.userJpaRepository = userJpaRepository;
    this.userMapper = userMapper;
  }

  @Override
  public Optional<User> findByUsername(String userName) {
    return userJpaRepository.findByUserName(userName).map(userMapper::toDomain);
  }

  @Override
  public void deleteById(UUID id) {
    userJpaRepository.deleteById(id);
  }

  @Override
  public boolean existsById(UUID id) {
    return userJpaRepository.existsById(id);
  }

  @Override
  public List<User> findAll() {
    return userJpaRepository.findAll().stream().map(userMapper::toDomain).toList();
  }

  @Override
  public User save(User user) {
    UserModel userModel = userMapper.toModel(user);
    return userMapper.toDomain(userJpaRepository.save(userModel));
  }

  @Override
  public boolean existsByUsername(String username) {
    return userJpaRepository.existsByUserName(username);
  }

}

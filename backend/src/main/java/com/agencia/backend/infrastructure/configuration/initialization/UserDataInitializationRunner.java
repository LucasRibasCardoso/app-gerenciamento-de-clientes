package com.agencia.backend.infrastructure.configuration.initialization;

import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.repository.UserRepository;
import java.util.Set;
import java.util.UUID;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserDataInitializationRunner implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserDataInitializationRunner(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    insertUsers();
  }

  private void insertUsers() {
    String passwordEncrypted = passwordEncoder.encode("12345678@");

    User userAdmin = new User(UUID.randomUUID(), "administration", passwordEncrypted, Set.of(Role.ADMIN));
    User userNormal = new User(UUID.randomUUID(), "commonUser", passwordEncrypted, Set.of(Role.USER));

    userRepository.save(userAdmin);
    userRepository.save(userNormal);
  }

}

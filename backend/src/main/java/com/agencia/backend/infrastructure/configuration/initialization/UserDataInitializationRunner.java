package com.agencia.backend.infrastructure.configuration.initialization;

import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.domain.repository.UserRepository;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserDataInitializationRunner implements CommandLineRunner {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  public UserDataInitializationRunner(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    User user1 = new User(null, "userCommon", passwordEncoder.encode("12345678@"), Set.of(Role.USER));
    User user2 = new User(null, "userAdmin", passwordEncoder.encode("12345678@"), Set.of(Role.ADMIN));

    userRepository.save(user1);
    userRepository.save(user2);
  }

}

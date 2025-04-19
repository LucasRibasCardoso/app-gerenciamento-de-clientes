package com.agencia.backend.infrastructure.configuration.initialization;



import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class UserDataInitializationRunner implements CommandLineRunner {

  private UserRepository userRepository;
  private PasswordEncoder passwordEncoder;

  public UserDataInitializationRunner(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public void run(String... args) {
    User user1 = new User(null, "userCommon", passwordEncoder.encode("12345678@"), Role.USER);
    User user2 = new User(null, "userAdmin", passwordEncoder.encode("12345678@"), Role.ADMIN);
    User user3 = new User(null, "userManager", passwordEncoder.encode("12345678@"), Role.MANAGER);

    userRepository.save(user1);
    userRepository.save(user2);
    userRepository.save(user3);
  }

}

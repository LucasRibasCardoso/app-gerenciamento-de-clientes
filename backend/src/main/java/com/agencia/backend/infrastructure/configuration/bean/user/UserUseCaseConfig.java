package com.agencia.backend.infrastructure.configuration.bean.user;

import com.agencia.backend.application.services.UserExistenceValidationService;
import com.agencia.backend.application.services.UserFieldUpdateService;
import com.agencia.backend.application.services.implementation.UserExistenceValidationServiceImp;
import com.agencia.backend.application.services.implementation.UserFieldUpdateServiceImp;
import com.agencia.backend.application.useCase.user.FindUserByIdUseCase;
import com.agencia.backend.application.useCase.user.UpdateUserUseCase;
import com.agencia.backend.application.useCase.user.implementation.FindUserByIdUseCaseImp;
import com.agencia.backend.application.useCase.user.implementation.UpdateUserUseCaseImp;
import com.agencia.backend.domain.repository.UserRepository;
import com.agencia.backend.application.useCase.user.RegisterUserUseCase;
import com.agencia.backend.application.useCase.user.DeleteUserUseCase;
import com.agencia.backend.application.useCase.user.FindAllUserUseCase;
import com.agencia.backend.application.useCase.user.FindUserByUsernameUseCase;
import com.agencia.backend.application.useCase.user.implementation.RegisterUserUseCaseImp;
import com.agencia.backend.application.useCase.user.implementation.DeleteUserUseCaseImp;
import com.agencia.backend.application.useCase.user.implementation.FindAllUserUseCaseImp;
import com.agencia.backend.application.useCase.user.implementation.FindUserByUsernameUseCaseImp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UserUseCaseConfig {

    @Bean
    public UserExistenceValidationService userExistenceValidationService(UserRepository userRepository) {
        return new UserExistenceValidationServiceImp(userRepository) {
        };
    }

    @Bean
    public UserFieldUpdateService userFieldUpdateService() {
        return new UserFieldUpdateServiceImp();
    }

    @Bean
    public FindUserByUsernameUseCase loadByUsernameUseCase(UserRepository userRepository) {
        return new FindUserByUsernameUseCaseImp(userRepository);
    }

    @Bean
    public FindAllUserUseCase findAllUserUseCase(UserRepository userRepository) {
        return new FindAllUserUseCaseImp(userRepository);
    }

    @Bean
    public RegisterUserUseCase registerUserUseCase(
            PasswordEncoder passwordEncoder, UserRepository userRepository
    ) {
        return new RegisterUserUseCaseImp(passwordEncoder, userRepository);
    }

    @Bean
    public DeleteUserUseCase deleteUserUseCase(UserRepository userRepository) {
        return new DeleteUserUseCaseImp(userRepository);
    }

    @Bean
    public FindUserByIdUseCase findUserByIdUseCase(UserRepository userRepository) {
        return new FindUserByIdUseCaseImp(userRepository);
    }

    @Bean
    public UpdateUserUseCase updateUserUseCase(
            UserRepository userRepository,
            FindUserByIdUseCase findUserByIdUseCase,
            UserFieldUpdateService userFieldUpdateService,
            UserExistenceValidationService userExistenceValidationService
    ) {
        return new UpdateUserUseCaseImp(
                userRepository,
                findUserByIdUseCase,
                userFieldUpdateService,
                userExistenceValidationService);
    }
}

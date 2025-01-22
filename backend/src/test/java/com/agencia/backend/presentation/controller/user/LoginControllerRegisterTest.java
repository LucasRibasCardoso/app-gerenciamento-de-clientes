package com.agencia.backend.presentation.controller.user;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.application.useCase.user.RegisterUserUseCase;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.domain.exceptions.user.InvalidPasswordException;
import com.agencia.backend.domain.exceptions.user.InvalidRoleException;
import com.agencia.backend.domain.exceptions.user.InvalidUsernameException;
import com.agencia.backend.infrastructure.configuration.jwt.JwtUtils;
import com.agencia.backend.presentation.controller.LoginController;
import com.agencia.backend.presentation.dto.user.UserRequestDTO;
import com.agencia.backend.presentation.dto.user.UserResponseDTO;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(LoginController.class)
public class LoginControllerRegisterTest {

  @MockitoBean
  private RegisterUserUseCase createUserUseCase;

  @MockitoBean
  private UserMapper userMapper;

  @MockitoBean
  private ValidateUserRequest validateUserRequest;

  @MockitoBean
  private AuthenticationManager authenticationManager;

  @MockitoBean
  private JwtUtils jwtUtils;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void ShouldRegisterUser_WhenUserIsValid() throws Exception {
    // Arrange
    User userDomain = new User(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"),
        "userCommon", "12345678@", Set.of(Role.USER));

    UserRequestDTO requestDTO = new UserRequestDTO(
        "userCommon", "12345678@", Set.of(Role.USER.toString()));

    UserResponseDTO responseDTO = new UserResponseDTO(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"),
        "userCommon", Set.of(Role.USER));

    when(userMapper.toDomain(requestDTO)).thenReturn(userDomain);
    when(createUserUseCase.register(userDomain)).thenReturn(userDomain);
    when(userMapper.toDTO(userDomain)).thenReturn(responseDTO);

    String requestBody = objectMapper.writeValueAsString(requestDTO);
    String responseBody = objectMapper.writeValueAsString(requestDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/register")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(header().exists("Location"))
        .andExpect(jsonPath("$.id").value("a536387d-89e3-492c-8e08-24c360e79335"))
        .andExpect(jsonPath("$.username").value("userCommon"))
        .andExpect(jsonPath("$.roles[0]").value("USER"));

    validateUserRequest.validateUsername(requestDTO.username());
    validateUserRequest.validatePassword(requestDTO.password());
    validateUserRequest.validateRoles(requestDTO.roles());
  }

  @Test
  void ShouldThrowException_WhenUsernameContainsSpecialCharacter() throws Exception {
    // Arrange
    UserRequestDTO requestDTO = new UserRequestDTO(
        "userCommon@", "12345678@", Set.of(Role.USER.toString()));

    doThrow(new InvalidUsernameException("O nome de usuário não pode conter caracteres especiais."))
        .when(validateUserRequest).validateUsername(requestDTO.username());

    String requestBody = objectMapper.writeValueAsString(requestDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("O nome de usuário não pode conter caracteres especiais."))
        .andExpect(jsonPath("$.statusCode").value("400"));
  }

  @Test
  void ShouldThrowException_WhenUsernameDontStartWithLetter() throws Exception {
    // Arrange
    UserRequestDTO requestDTO = new UserRequestDTO(
        "123userCommon", "12345678@", Set.of(Role.USER.toString()));

    doThrow(new InvalidUsernameException("O nome de usuário deve começar com uma letra."))
      .when(validateUserRequest).validateUsername(requestDTO.username());

    String requestBody = objectMapper.writeValueAsString(requestDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("O nome de usuário deve começar com uma letra."))
        .andExpect(jsonPath("$.statusCode").value("400"));
  }

  @Test
  void ShouldThrowException_WhenPasswordDontContainSpecialCharacter() throws Exception {
    // Arrange
    UserRequestDTO requestDTO = new UserRequestDTO("userCommon", "password123", Set.of(Role.USER.toString()));

    doThrow(new InvalidPasswordException("A senha deve conter pelo menos um caractere especial."))
        .when(validateUserRequest).validatePassword(requestDTO.password());

    String requestBody = objectMapper.writeValueAsString(requestDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("A senha deve conter pelo menos um caractere especial."))
        .andExpect(jsonPath("$.statusCode").value("400"));
  }

  @Test
  void ShouldThrowException_WhenListOfRolesIsEmpty() throws Exception {
    // Arrange
    UserRequestDTO requestDTO = new UserRequestDTO("userCommon", "12345678@", Set.of());

    doThrow(new InvalidRoleException("A lista de funções não pode estar vazia."))
        .when(validateUserRequest).validateRoles(requestDTO.roles());

    String requestBody = objectMapper.writeValueAsString(requestDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("A lista de funções não pode estar vazia."))
        .andExpect(jsonPath("$.statusCode").value("400"));
  }

  @Test
  void ShouldThrowException_WhenRoleIsInvalid() throws Exception {
    // Arrange
    UserRequestDTO requestDTO = new UserRequestDTO("userCommon", "12345678@", Set.of("INVALID_ROLE"));

    doThrow(new InvalidRoleException("Permissões inválidas: " + requestDTO.roles()))
      .when(validateUserRequest).validateRoles(requestDTO.roles());

    String requestBody = objectMapper.writeValueAsString(requestDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Permissões inválidas: " + requestDTO.roles()))
        .andExpect(jsonPath("$.statusCode").value("400"));
  }

  @Test
  void ShouldThrowException_WhenUserRequestIsInvalid() throws Exception {
    // Arrange
    UserRequestDTO requestDTO = new UserRequestDTO(null, "1234", null);

    String requestBody = objectMapper.writeValueAsString(requestDTO);

    // Act & Assert
    mockMvc.perform(post("/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(content().json("""
         {
           "message": "Erro de validação nos campos enviados",
           "statusCode": 400,
             "errors": [
               { "field": "username", "message": "O username precisa ser informado" },
               { "field": "password", "message": "A senha deve ter entre 8 e 20 caracteres" },
               { "field": "roles", "message": "A lista de permissões é obrigatória e não pode ser nula" }
             ]
         }
         """));
  }
}

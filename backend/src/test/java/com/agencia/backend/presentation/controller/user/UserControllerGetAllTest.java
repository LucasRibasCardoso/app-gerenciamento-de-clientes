package com.agencia.backend.presentation.controller.user;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.application.useCase.user.DeleteUserUseCase;
import com.agencia.backend.application.useCase.user.FindAllUserUseCase;
import com.agencia.backend.domain.entity.User;
import com.agencia.backend.domain.entity.enuns.Role;
import com.agencia.backend.presentation.controller.UserController;
import com.agencia.backend.presentation.dto.user.UserResponseDTO;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
public class UserControllerGetAllTest {

  @MockitoBean
  private UserMapper userMapper;

  @MockitoBean
  private FindAllUserUseCase findAllUserUseCase;

  @MockitoBean
  private DeleteUserUseCase deleteUserUseCase;

  @MockitoBean
  private ValidateUserRequest validateUserRequest;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void ShouldReturnListOfUsers_WhenUsersExist() throws Exception {
    // Arrange
    User user = new User(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"),
        "userCommon", "12345678@", Set.of(Role.USER));
    List<User> users = List.of(user);

    UserResponseDTO userDTO = new UserResponseDTO(
        UUID.fromString("a536387d-89e3-492c-8e08-24c360e79335"),
        "userCommon", Set.of(Role.USER)
    );

    when(findAllUserUseCase.getAllUsers()).thenReturn(users);
    when(userMapper.toDTO(user)).thenReturn(userDTO);

    String expectedJson = objectMapper.writeValueAsString(List.of(userDTO));

    // Act & Assert
    mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(expectedJson));
  }

  @Test
  void ShouldReturnEmptyList_WhenUsersDoesNotExist() throws Exception {
    // Arrange

    when(findAllUserUseCase.getAllUsers()).thenReturn(List.of());
    when(userMapper.toDTO(any(User.class))).thenReturn(null);

    String responseBody = objectMapper.writeValueAsString(List.of());

    // Act & Assert
    mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(responseBody));
  }
}

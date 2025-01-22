package com.agencia.backend.presentation.controller.user;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.application.useCase.user.DeleteUserUseCase;
import com.agencia.backend.application.useCase.user.FindAllUserUseCase;
import com.agencia.backend.domain.exceptions.user.InvalidUUIDException;
import com.agencia.backend.domain.exceptions.user.UserNotFoundException;
import com.agencia.backend.presentation.controller.UserController;
import com.agencia.backend.presentation.mapper.user.UserMapper;
import com.agencia.backend.presentation.validators.user.ValidateUserRequest;
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
class UserControllerDeleteTest {

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
  void ShouldDeleteUser_WhenFoundUser() throws Exception {
    //Arrange
    String userId = "a536387d-89e3-492c-8e08-24c360e79335";

    // Act & Assert
    mockMvc.perform(delete("/users/{id}", userId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void ShouldThrowException_WhenUserNotFound() throws Exception {
    // Arrange
    String userId = "a536387d-89e3-492c-8e08-24c360e79335";

    doThrow(new UserNotFoundException("Nenhum usuário encontrado."))
        .when(deleteUserUseCase).deleteUser(UUID.fromString(userId));

    // Act & Assert
    mockMvc.perform(delete("/users/{id}", userId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("Nenhum usuário encontrado."))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }

  @Test
  void ShouldThrowException_WhenUuidIsInvalid() throws Exception {
    // Arrange
    String userId = "a536387d";

    doThrow(new InvalidUUIDException("O UUID fornecido não é válido.")).when(validateUserRequest).validateUUID(userId);

    // Act & Assert
    mockMvc.perform(delete("/users/{id}", userId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("O UUID fornecido não é válido."))
        .andExpect(jsonPath("$.statusCode").value("400"));
  }

}
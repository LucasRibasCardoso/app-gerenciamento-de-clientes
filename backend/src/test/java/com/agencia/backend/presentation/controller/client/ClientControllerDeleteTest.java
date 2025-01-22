package com.agencia.backend.presentation.controller.client;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.domain.exceptions.client.ClientNotFoundException;
import com.agencia.backend.presentation.controller.ClientController;
import com.agencia.backend.presentation.mapper.client.ClientMapper;
import com.agencia.backend.presentation.validators.client.UrlParametersValidator;
import com.agencia.backend.application.useCase.client.CreateClientUseCase;
import com.agencia.backend.application.useCase.client.DeleteClientUseCase;
import com.agencia.backend.application.useCase.client.FindAllClientUseCase;
import com.agencia.backend.application.useCase.client.UpdateClientUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClientController.class)
public class ClientControllerDeleteTest {

  @MockitoBean
  private CreateClientUseCase createClientUseCase;
  @MockitoBean
  private FindAllClientUseCase findAllClientUseCase;
  @MockitoBean
  private DeleteClientUseCase deleteClientUseCase;
  @MockitoBean
  private UpdateClientUseCase updateClientUseCase;
  @MockitoBean
  private UrlParametersValidator urlParametersValidator;
  @MockitoBean
  private ClientMapper clientMapper;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  void ShouldDeleteClient_WhenFoundClient() throws Exception {
    // Arrange
    Long clientId = 1L;

    // Act & Assert
    mockMvc.perform(delete("/clients/{id}", clientId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  @Test
  void ShouldThrowException_WhenClientNotFound() throws Exception {
    // Arrange
    Long clientId = 1L;

    doThrow(new ClientNotFoundException("Nenhum cliente encontrado com o ID: " + clientId)).when(
            deleteClientUseCase).deleteClient(clientId);

    // Act & Assert
    mockMvc.perform(delete("/clients/{id}", clientId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("Nenhum cliente encontrado com o ID: 1"))
        .andExpect(jsonPath("$.statusCode").value("404"));
  }
}

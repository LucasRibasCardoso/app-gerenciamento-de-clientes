package com.agencia.backend.presentation.controller.client;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.agencia.backend.application.useCase.client.CreateClientUseCase;
import com.agencia.backend.application.useCase.client.DeleteClientUseCase;
import com.agencia.backend.application.useCase.client.FindAllClientUseCase;
import com.agencia.backend.application.useCase.client.FindClientByIdUseCase;
import com.agencia.backend.application.useCase.client.UpdateClientUseCase;
import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
import com.agencia.backend.domain.exceptions.client.ClientNotFoundException;
import com.agencia.backend.presentation.controller.ClientController;
import com.agencia.backend.presentation.dto.address.AddressDTO;
import com.agencia.backend.presentation.dto.client.ClientResponseDTO;
import com.agencia.backend.presentation.dto.passport.PassportDTO;
import com.agencia.backend.presentation.mapper.client.ClientMapper;
import com.agencia.backend.presentation.validators.client.UrlParametersValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClientController.class)
public class ClientControllerGetByIdTest {

  @MockitoBean
  private CreateClientUseCase createClientUseCase;
  @MockitoBean
  private FindAllClientUseCase findAllClientUseCase;
  @MockitoBean
  private FindClientByIdUseCase findClientByIdUseCase;
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

  private ClientResponseDTO clientResponseDTO() {
    return new ClientResponseDTO(
        1L,
        "João Da Silva",
        "497.494.050-30",
        "01/01/1990",
        "(11) 98765-4321",
        "joao.silva@example.com",
        new PassportDTO(
            "AB123456", "01/06/2020", "01/06/2030"
        ),
        new AddressDTO(
            "12345-678",
            "Brasil",
            "SP",
            "São Paulo",
            "Jardim Primavera",
            "Rua Das Flores",
            "Apto 101",
            "123"
        )
    );
  }
  private Client createClientDomain() {
    return new Client(
        1L,
        "João Da Silva",
        "497.494.050-30",
        LocalDate.of(1990, 1, 1),
        "(11) 98765-4321",
        "joao.silva@example.com",
        new Passport(
            "AB123456", LocalDate.of(2020, 6, 1), LocalDate.of(2030, 6, 1)
        ),
        new Address(
            "12345-678",
            "Brasil",
            "SP",
            "São Paulo",
            "Jardim Primavera",
            "Rua Das Flores",
            "Apto 101",
            "123"
        )
    );
  }

  @Test
  void ShouldReturnClient_WhenClientIsFound() throws Exception {
    // Arrange
    Long clientId = 1L;
    Client client = createClientDomain();

    when(clientMapper.toDTO(client)).thenReturn(clientResponseDTO());
    when(findClientByIdUseCase.getClient(clientId)).thenReturn(client);

    String responseExpected = objectMapper.writeValueAsString(clientResponseDTO());

    // Act
    mockMvc.perform(get("/clients/{id}", clientId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(responseExpected));

    verify(urlParametersValidator).validateID(clientId);
  }

  @Test
  void ShouldReturnClient_WhenClientIsNotFound() throws Exception {
    // Arrange
    Long clientId = 1L;

    doThrow(new ClientNotFoundException("Nenhum cliente encontrado com o ID: " + clientId))
        .when(findClientByIdUseCase).getClient(clientId);

    // Act & Assert
    mockMvc.perform(get("/clients/{id}", clientId)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.message").value("Nenhum cliente encontrado com o ID: 1"))
        .andExpect(jsonPath("$.statusCode").value("404"));

    verify(urlParametersValidator).validateID(clientId);
  }
}

package com.agencia.backend.presentation.controller.client;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.presentation.controller.ClientController;
import com.agencia.backend.presentation.dto.address.AddressDTO;
import com.agencia.backend.presentation.dto.client.ClientRequestUpdateDTO;
import com.agencia.backend.presentation.dto.client.ClientResponseDTO;
import com.agencia.backend.presentation.dto.passport.PassportDTO;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.presentation.mapper.client.ClientMapper;
import com.agencia.backend.presentation.validators.client.UrlParametersValidator;
import com.agencia.backend.application.useCase.client.CreateClientUseCase;
import com.agencia.backend.application.useCase.client.DeleteClientUseCase;
import com.agencia.backend.application.useCase.client.FindAllClientUseCase;
import com.agencia.backend.application.useCase.client.UpdateClientUseCase;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClientController.class)
public class ClientControllerUpdateTest {

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

  private ClientRequestUpdateDTO createUpdateRequest() {
    return new ClientRequestUpdateDTO(
        "Updated Name",
        "15/05/1995",
        "(11) 98765-4321",
        "updated.email@example.com",
        new PassportDTO("AB123456", "01/06/2020", "01/06/2030"),
        new AddressDTO(
            "12345-678",
            "Brasil",
            "SP",
            "São Paulo",
            "Jardim Atualizado",
            "Rua Nova",
            "Apto 202",
            "456"
        )
    );
  }

  private ClientResponseDTO createUpdateResponse() {
    return new ClientResponseDTO(
        1L,
        "Updated Name",
        "497.494.050-30",
        "15/05/1995",
        "(11) 98765-4321",
        "updated.email@example.com",
        new PassportDTO("AB123456", "01/06/2020", "01/06/2030"),
        new AddressDTO(
            "12345-678",
            "Brasil",
            "SP",
            "São Paulo",
            "Jardim Atualizado",
            "Rua Nova",
            "Apto 202",
            "456"
        )
    );
  }

  private Client createUpdateDomain() {
    return new Client(
        1L,
        "Updated Name",
        "497.494.050-30",
        LocalDate.of(1995, 5, 15),
        "(11) 98765-4321",
        "updated.email@example.com",
        null,
        null
    );
  }

  @Test
  void ShouldUpdateClientSuccessfully() throws Exception {
    // Arrange
    Long clientId = 1L;
    ClientRequestUpdateDTO request = createUpdateRequest();
    ClientResponseDTO response = createUpdateResponse();
    Client mockClient = createUpdateDomain();

    String requestBody = objectMapper.writeValueAsString(request);
    String responseBody = objectMapper.writeValueAsString(response);

    when(clientMapper.toDomain(request)).thenReturn(mockClient);
    when(updateClientUseCase.update(clientId, mockClient)).thenReturn(mockClient);
    when(clientMapper.toDTO(mockClient)).thenReturn(response);

    // Act & Assert
    mockMvc.perform(put("/clients/{id}", clientId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(responseBody));
  }

  @Test
  void shouldReturnBadRequest_WhenDataIsInvalid() throws Exception {
    // Arrange
    Long clientId = 1L;
    ClientRequestUpdateDTO invalidRequest = new ClientRequestUpdateDTO(
        null,
        "Invalid-Date", // Data de nascimento inválida
        "(11) 98765-4321",
        "updated.emailexample.com", // Email inválido
        new PassportDTO("AB123456", "01/06/2020", "01/06/2030"),
        new AddressDTO(
            "12345-678",
            "Brasil",
            "SP",
            "São Paulo",
            "Jardim Atualizado",
            "Rua Nova",
            "Apto 202",
            "456"
        )
    );

    String requestBody = objectMapper.writeValueAsString(invalidRequest);

    // Act & Assert
    mockMvc.perform(put("/clients/{id}", clientId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("""
                {
                  "message": "Erro de validação nos campos enviados",
                  "statusCode": 400,
                  "errors": [
                    { "field": "email", "message": "E-mail inválido" },
                    { "field": "birthDate", "message": "Data de nascimento deve estar no formato dd/MM/yyyy" }
                  ]
                }
                """));
  }
}

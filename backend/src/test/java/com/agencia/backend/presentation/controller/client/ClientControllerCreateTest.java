package com.agencia.backend.presentation.controller.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.presentation.controller.ClientController;
import com.agencia.backend.presentation.dto.address.AddressDTO;
import com.agencia.backend.presentation.dto.client.ClientRequestDTO;
import com.agencia.backend.presentation.dto.client.ClientResponseDTO;
import com.agencia.backend.presentation.dto.passport.PassportDTO;
import com.agencia.backend.domain.entity.Address;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.entity.Passport;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClientController.class)
class ClientControllerCreateTest {

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


  private ClientRequestDTO createClientRequestDTO() {
    PassportDTO passportRequest = new PassportDTO("AB123456", "01/06/2020", "01/06/2030");

    AddressDTO addressRequest = new AddressDTO("12345-678", "Brasil", "SP", "São Paulo", "Jardim Primavera",
        "Rua das Flores", "Apto 101", "123"
    );

    ClientRequestDTO clientRequest = new ClientRequestDTO("João da Silva", "497.494.050-30", "15/05/1990",
        "(11) 98765-4321", "joao.silva@example.com", passportRequest, addressRequest
    );

    return clientRequest;
  }
  private ClientResponseDTO createClientResponseDTO() {
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
  void ShouldReturnClientResponseDTO_WhenCreateClientSuccessfully() throws Exception {
    // Arrange
    ClientRequestDTO request = createClientRequestDTO();
    ClientResponseDTO response = createClientResponseDTO();
    Client mockClient = createClientDomain();

    when(clientMapper.toDomain(request)).thenReturn(mockClient);
    when(clientMapper.toDTO(any(Client.class))).thenReturn(response);
    when(createClientUseCase.createClient(any(Client.class))).thenReturn(mockClient);

    String requestBody = objectMapper.writeValueAsString(request);
    String responseBody = objectMapper.writeValueAsString(response);

    // Act & Assert
    mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "http://localhost/clients/1"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().string(responseBody));
  }

  @Test
  void ShouldThrowExceptionBadRequest_WhenDataIsInvalid() throws Exception {
    // Arrange
    ClientRequestDTO invalidRequest = new ClientRequestDTO(
        "", // Nome vazio
        "1233123123", // CPF inválido
        "2023-Dezembro-12", // Data de nascimento inválida
        "(11) 98765-4321",
        "joao.silva@example.com",
        new PassportDTO(
            "AB123456",
            "01/06/2020",
            "01/06/2030"),
        new AddressDTO(
            "12345-678",
            "Brasil",
            "SP",
            "São Paulo",
            "Jardim Primavera",
            "Rua das Flores",
            "Apto 101", "123"
        )
    );

    String requestBody = objectMapper.writeValueAsString(invalidRequest);

    // Act & Assert
    mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON)
            .content(requestBody))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json("""
            {
              "message": "Erro de validação nos campos enviados",
              "statusCode": 400,
              "errors": [
                { "field": "completeName", "message": "O nome completo é obrigatório" },
                { "field": "cpf", "message": "CPF inválido" },
                { "field": "birthDate", "message": "Data de nascimento deve estar no formato dd/MM/yyyy" }
              ]
            }
            """));
  }


}
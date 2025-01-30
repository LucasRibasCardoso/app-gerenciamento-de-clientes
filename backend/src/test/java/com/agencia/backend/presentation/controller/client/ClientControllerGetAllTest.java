package com.agencia.backend.presentation.controller.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.agencia.backend.presentation.controller.ClientController;
import com.agencia.backend.presentation.dto.address.AddressDTO;
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
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClientController.class)
public class ClientControllerGetAllTest {

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

  private ClientResponseDTO createClientResponseDTO() {
    return new ClientResponseDTO(1L, "João Da Silva", "497.494.050-30", "01/01/1990", "(11) 98765-4321",
        "joao.silva@example.com", new PassportDTO("AB123456", "01/06/2020", "01/06/2030"),
        new AddressDTO("12345-678", "Brasil", "SP", "São Paulo", "Jardim Primavera", "Rua Das Flores",
            "Apto 101", "123"
        )
    );
  }

  private Client createClientDomain() {
    return new Client(1L, "João Da Silva", "497.494.050-30", LocalDate.of(1990, 1, 1), "(11) 98765-4321",
        "joao.silva@example.com",
        new Passport("AB123456", LocalDate.of(2020, 6, 1), LocalDate.of(2030, 6, 1)),
        new Address("12345-678", "Brasil", "SP", "São Paulo", "Jardim Primavera", "Rua Das Flores",
            "Apto 101", "123"
        )
    );
  }

  @Test
  void ShouldReturnListClient_WhenGetAllClientsSuccessfully() throws Exception {
    // Arrange
    String search = null;
    String orderBy = "id";
    String sortOrder = "desc";
    int page = 0;
    int size = 10;

    Client client = createClientDomain();
    ClientResponseDTO clientDTO = createClientResponseDTO();

    // Simula uma página com um cliente
    Page<Client> clientPage = new PageImpl<>(List.of(client), PageRequest.of(page, size), 1);

    when(findAllClientUseCase.getClients(search, orderBy, sortOrder, page, size)).thenReturn(clientPage);
    when(clientMapper.toDTO(client)).thenReturn(clientDTO);

    // Corpo da resposta esperada
    String responseBody = """
        {
            "data": [
                {
                    "id": 1,
                    "completeName": "João Da Silva",
                    "cpf": "497.494.050-30",
                    "birthDate": "01/01/1990",
                    "phone": "(11) 98765-4321",
                    "email": "joao.silva@example.com",
                    "passport": {
                        "number": "AB123456",
                        "emissionDate": "01/06/2020",
                        "expirationDate": "01/06/2030"
                    },
                    "address": {
                        "zipCode": "12345-678",
                        "country": "Brasil",
                        "state": "SP",
                        "city": "São Paulo",
                        "neighborhood": "Jardim Primavera",
                        "street": "Rua Das Flores",
                        "complement": "Apto 101",
                        "residentialNumber": "123"
                    }
                }
            ],
            "totalPages": 1,
            "totalElements": 1
        }
        """;

    // Act & Assert
    mockMvc.perform(get("/clients")
            .param("orderBy", orderBy)
            .param("sortOrder", sortOrder)
            .param("page", String.valueOf(page))
            .param("size", String.valueOf(size))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(responseBody));
  }

  @Test
  void ShouldReturnEmptyList_WhenGetAllClientsReturnsEmptyList() throws Exception {
    // Arrange
    String search = null;
    String orderBy = "id";
    String sortOrder = "desc";
    int page = 0;
    int size = 10;

    // Simula uma página vazia
    Page<Client> clientPage = new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), 0);

    when(findAllClientUseCase.getClients(search, orderBy, sortOrder, page, size)).thenReturn(clientPage);

    // Corpo da resposta esperada
    String responseBody = """
        {
            "data": [],
            "totalPages": 0,
            "totalElements": 0
        }
        """;

    // Act & Assert
    mockMvc.perform(get("/clients")
            .param("orderBy", orderBy)
            .param("sortOrder", sortOrder)
            .param("page", String.valueOf(page))
            .param("size", String.valueOf(size))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().json(responseBody));
  }
}

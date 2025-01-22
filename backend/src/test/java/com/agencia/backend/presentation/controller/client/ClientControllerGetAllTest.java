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

    when(findAllClientUseCase.getClients(search, orderBy, sortOrder, page, size)).thenReturn(List.of(client));
    when(clientMapper.toDTO(client)).thenReturn(clientDTO);

    String responseBody = objectMapper.writeValueAsString(List.of(clientDTO));

    // Act & Assert
    mockMvc.perform(get("/clients").param("orderBy", orderBy)
            .param("sortOrder", sortOrder)
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().string(responseBody));

    // Verify
    verify(urlParametersValidator).validateOrderBy(orderBy);
    verify(urlParametersValidator).validateSortOrder(sortOrder);
    verify(findAllClientUseCase).getClients(search, orderBy, sortOrder, page, size);
    verify(clientMapper).toDTO(client);
  }

  @Test
  void ShouldReturnEmptyList_WhenGetAllClientsReturnsEmptyList() throws Exception {
    // Arrange
    String search = null;
    String orderBy = "id";
    String sortOrder = "desc";
    int page = 0;
    int size = 10;

    when(findAllClientUseCase.getClients(search, orderBy, sortOrder, page, size)).thenReturn(Collections.emptyList());

    String responseBody = objectMapper.writeValueAsString(List.of());

    // Act & Assert
    mockMvc.perform(get("/clients").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().json(responseBody));
  }
}

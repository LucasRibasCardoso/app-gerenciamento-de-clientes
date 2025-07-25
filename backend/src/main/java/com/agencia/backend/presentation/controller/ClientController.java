package com.agencia.backend.presentation.controller;

import com.agencia.backend.application.useCase.client.FindClientByIdUseCase;
import com.agencia.backend.presentation.dto.client.ClientRequestDTO;
import com.agencia.backend.presentation.dto.client.ClientRequestUpdateDTO;
import com.agencia.backend.presentation.dto.client.ClientResponseDTO;
import com.agencia.backend.presentation.dto.pagination.PaginatedResponse;
import com.agencia.backend.presentation.mapper.client.ClientMapper;
import com.agencia.backend.presentation.validators.client.UrlParametersValidator;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.application.useCase.client.CreateClientUseCase;
import com.agencia.backend.application.useCase.client.DeleteClientUseCase;
import com.agencia.backend.application.useCase.client.FindAllClientUseCase;
import com.agencia.backend.application.useCase.client.UpdateClientUseCase;
import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final CreateClientUseCase createClientUseCase;
    private final FindAllClientUseCase findAllClientUseCase;
    private final FindClientByIdUseCase findClientByIdUseCase;
    private final DeleteClientUseCase deleteClientUseCase;
    private final UpdateClientUseCase updateClientUseCase;

    private final UrlParametersValidator urlParametersValidator;
    private final ClientMapper clientMapper;

    public ClientController(
            CreateClientUseCase createClientUseCase,
            FindAllClientUseCase findAllClientUseCase,
            FindClientByIdUseCase findClientByIdUseCase,
            DeleteClientUseCase deleteClientUseCase,
            UpdateClientUseCase updateClientUseCase,
            UrlParametersValidator urlParametersValidator,
            ClientMapper clientMapper
    ) {
        this.createClientUseCase = createClientUseCase;
        this.findAllClientUseCase = findAllClientUseCase;
        this.findClientByIdUseCase = findClientByIdUseCase;
        this.deleteClientUseCase = deleteClientUseCase;
        this.updateClientUseCase = updateClientUseCase;
        this.urlParametersValidator = urlParametersValidator;
        this.clientMapper = clientMapper;
    }


    // CREATE
    @PostMapping
    public ResponseEntity<ClientResponseDTO> createClient(@Valid @RequestBody ClientRequestDTO request) {
        Client clientSaved = createClientUseCase.createClient(clientMapper.toDomain(request));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(clientSaved.getId())
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).location(location).body(clientMapper.toDTO(clientSaved));
    }


    // READ
    @GetMapping
    public ResponseEntity<PaginatedResponse<ClientResponseDTO>> getAllClients(
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "id") String orderBy,
            @RequestParam(required = false, defaultValue = "asc") String sortOrder,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        urlParametersValidator.validateOrderBy(orderBy);
        urlParametersValidator.validateSortOrder(sortOrder);

        // Busca os clientes paginados
        Page<Client> clientsPage = findAllClientUseCase
                .getClients(search, orderBy, sortOrder, page, size);

        // Mapeia para DTO
        List<ClientResponseDTO> clientResponse = clientsPage
                .stream()
                .map(clientMapper::toDTO)
                .collect(Collectors.toList());

        // Retorna a resposta paginada
        return ResponseEntity.ok(new PaginatedResponse<>(
                clientResponse,
                clientsPage.getTotalPages(),
                clientsPage.getTotalElements()
        ));
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getClientById(@PathVariable Long id) {
        urlParametersValidator.validateID(id);

        Client client = findClientByIdUseCase.getClient(id);
        return ResponseEntity.ok(clientMapper.toDTO(client));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequestUpdateDTO request) {
        urlParametersValidator.validateID(id);
        Client clientUpdated = updateClientUseCase.update(id, clientMapper.toDomain(request));

        return ResponseEntity.status(HttpStatus.OK).body(clientMapper.toDTO(clientUpdated));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        urlParametersValidator.validateID(id);
        deleteClientUseCase.deleteClient(id);


        return ResponseEntity.noContent().build();
    }

}

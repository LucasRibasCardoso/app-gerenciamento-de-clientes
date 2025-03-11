package com.agencia.backend.infrastructure.repository.client.implementation;

import com.agencia.backend.application.services.EncryptionService;
import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.infrastructure.model.ClientModel;
import com.agencia.backend.infrastructure.repository.client.ClientJpaRepository;
import com.agencia.backend.infrastructure.specifications.SpecificationBuilder;
import com.agencia.backend.presentation.mapper.client.ClientMapper;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositoryImp implements ClientRepository {

  private final ClientJpaRepository jpaRepository;
  private final ClientMapper clientMapper;
  private final SpecificationBuilder<ClientModel> specificationBuilder;
  private final EncryptionService encryptionService;

  public ClientRepositoryImp(
      ClientJpaRepository jpaRepository,
      ClientMapper clientMapper,
      SpecificationBuilder<ClientModel> specificationBuilder,
      EncryptionService encryptionService) {
    this.jpaRepository = jpaRepository;
    this.clientMapper = clientMapper;
    this.specificationBuilder = specificationBuilder;
    this.encryptionService = encryptionService;
  }

  @Override
  public Client save(Client client) {
    // Criptografa os dados
    ClientModel encryptedClient = toEncryptedClient(clientMapper.toModel(client), client);

    // Salva o modelo com dados criptografados
    ClientModel savedModel = jpaRepository.save(encryptedClient);

    // Converte de volta para domínio (CPF descriptografado)
    return clientMapper.toDomain(savedModel);
  }

  @Override
  public Optional<Client> findById(Long id) {
    Optional<ClientModel> selectedClient = jpaRepository.findById(id);

    // Descriptografa os dados antes de retorna-los, caso o cliente seja encontrado
    if (selectedClient.isPresent()) {
      return Optional.of(clientMapper.toDomain(toDecryptedClient(selectedClient.get())));
    }
    return Optional.empty();
  }

  @Override
  public Page<Client> findAll(String searchQuery, String orderBy, String sortOrder, int page, int size) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), orderBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Specification<ClientModel> specification = specificationBuilder.build(searchQuery);

    // Descriptografa os dados de cada cliente antes de retorna-los
    return jpaRepository.findAll(specification, pageable).map(model -> {
      toDecryptedClient(model);
      return clientMapper.toDomain(model);
    });
  }

  @Override
  public void deleteById(Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public boolean existsByCpf(String cpf) {
    String hashedCpf = encryptionService.hash(cpf);
    return jpaRepository.existsByHashedCpf(hashedCpf);
  }

  @Override
  public boolean existsByPassportNumber(String passportNumber) {
    String hashedPassportNumber = encryptionService.hash(passportNumber);
    return jpaRepository.existsByHashedPassportNumber(hashedPassportNumber);
  }

  @Override
  public boolean existsByEmail(String email) {
    return jpaRepository.existsByEmail(email);
  }

  @Override
  public boolean existsById(Long id) {
    return jpaRepository.existsById(id);
  }

  private ClientModel toDecryptedClient(ClientModel model){
    model.setRawCpf(encryptionService.decrypt(model.getRawCpf()));
    model.setRawPhone(encryptionService.decrypt(model.getRawPhone()));
    model.setRawPassportNumber(encryptionService.decrypt(model.getRawPassportNumber()));
    model.setRawZipCode(encryptionService.decrypt(model.getRawZipCode()));
    model.setRawCountry(encryptionService.decrypt(model.getRawCountry()));
    model.setRawState(encryptionService.decrypt(model.getRawState()));
    model.setRawCity(encryptionService.decrypt(model.getRawCity()));
    model.setRawNeighborhood(encryptionService.decrypt(model.getRawNeighborhood()));
    model.setRawStreet(encryptionService.decrypt(model.getRawStreet()));
    model.setRawComplement(encryptionService.decrypt(model.getRawComplement()));
    model.setRawResidentialNumber(encryptionService.decrypt(model.getRawResidentialNumber()));
    return model;
  }

  private ClientModel toEncryptedClient(ClientModel model, Client client) {
    model.setEncryptedCpf(encryptionService.encrypt(client.getCpf()));
    model.setHashedCpf(encryptionService.hash(client.getCpf()));
    model.setEncryptedPhone(encryptionService.encrypt(client.getPhone()));
    model.setEncryptedPassportNumber(encryptionService.encrypt(client.getPassport().getNumber()));
    model.setHashedPassportNumber(encryptionService.hash(client.getPassport().getNumber()));
    model.setEncryptedZipCode(encryptionService.encrypt(client.getAddress().getZipCode()));
    model.setEncryptedCountry(encryptionService.encrypt(client.getAddress().getCountry()));
    model.setEncryptedState(encryptionService.encrypt(client.getAddress().getState()));
    model.setEncryptedCity(encryptionService.encrypt(client.getAddress().getCity()));
    model.setEncryptedNeighborhood(encryptionService.encrypt(client.getAddress().getNeighborhood()));
    model.setEncryptedStreet(encryptionService.encrypt(client.getAddress().getStreet()));
    model.setEncryptedComplement(encryptionService.encrypt(client.getAddress().getComplement()));
    model.setEncryptedResidencialNumber(encryptionService.encrypt(client.getAddress().getResidentialNumber()));
    return model;
  }
}

package com.agencia.backend.infrastructure.repository.client.implementation;

import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.infrastructure.model.ClientModel;
import com.agencia.backend.infrastructure.repository.client.ClientJpaRepository;
import com.agencia.backend.infrastructure.specifications.SpecificationBuilder;
import com.agencia.backend.presentation.mapper.client.ClientMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

  public ClientRepositoryImp(
      ClientJpaRepository jpaRepository,
      ClientMapper clientMapper,
      SpecificationBuilder<ClientModel> specificationBuilder) {
    this.jpaRepository = jpaRepository;
    this.clientMapper = clientMapper;
    this.specificationBuilder = specificationBuilder;
  }

  @Override
  public Client save(Client client) {
    ClientModel model = clientMapper.toModel(client);
    ClientModel savedModel = jpaRepository.save(model);
    return clientMapper.toDomain(savedModel);
  }

  @Override
  public Optional<Client> findById(Long id) {
    Optional<ClientModel> model = jpaRepository.findById(id);
    return model.map(clientMapper::toDomain);
  }

  @Override
  public List<Client> findAll(String search, String orderBy, String sortOrder, int page, int size) {
    Sort sortOder = Sort.by(Sort.Direction.fromString(sortOrder), orderBy);
    Pageable pageable = PageRequest.of(page, size, sortOder);

    Specification<ClientModel> specification = specificationBuilder.build(search);

    return jpaRepository.findAll(specification, pageable)
        .getContent()
        .stream()
        .map(clientMapper::toDomain)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteById(Long id) {
    jpaRepository.deleteById(id);
  }

  @Override
  public boolean existsByCpf(String cpf) {
    return jpaRepository.existsByCpf(cpf);
  }

  @Override
  public boolean existsByPassportNumber(String passportNumber) {
    return jpaRepository.existsByPassportNumber(passportNumber);
  }

  @Override
  public boolean existsByEmail(String email) {
    return jpaRepository.existsByEmail(email);
  }

  @Override
  public boolean existsById(Long id) {
    return jpaRepository.existsById(id);
  }

}

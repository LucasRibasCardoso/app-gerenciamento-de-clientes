package com.agencia.backend.infrastructure.repository.client.implementation;

import com.agencia.backend.domain.entity.Client;
import com.agencia.backend.domain.repository.ClientRepository;
import com.agencia.backend.infrastructure.model.ClientModel;
import com.agencia.backend.infrastructure.repository.client.ClientJpaRepository;
import com.agencia.backend.infrastructure.specifications.SpecificationBuilder;
import com.agencia.backend.presentation.mapper.client.ClientMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

  public ClientRepositoryImp(
      ClientJpaRepository jpaRepository,
      ClientMapper clientMapper,
      SpecificationBuilder<ClientModel> specificationBuilder
  ) {
    this.jpaRepository = jpaRepository;
    this.clientMapper = clientMapper;
    this.specificationBuilder = specificationBuilder;
  }

  @Override
  public Client save(Client client) {
    ClientModel savedModel = jpaRepository.save(clientMapper.toModel(client));
    return clientMapper.toDomain(savedModel);
  }

  @Override
  public Optional<Client> findById(Long id) {
    return jpaRepository.findById(id)
        .map(clientMapper::toDomain);
  }

  @Override
  public Page<Client> findAll(String searchQuery, String orderBy, String sortOrder, int page, int size) {
    Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), orderBy);
    Pageable pageable = PageRequest.of(page, size, sort);

    Specification<ClientModel> specification = specificationBuilder.build(searchQuery);

    return jpaRepository.findAll(specification, pageable)
        .map(clientMapper::toDomain);
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
  public boolean existsByEmail(String email) {
    return jpaRepository.existsByEmail(email);
  }

  @Override
  public boolean existsById(Long id) {
    return jpaRepository.existsById(id);
  }

  @Override
  public Long getTotalClients() {
    return jpaRepository.count();
  }

  @Override
  public Long getTotalNewClientsLast30Days() {
    LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
    return jpaRepository.countByCreatedAtGreaterThanEqual(thirtyDaysAgo);
  }

  @Override
  public Long getTotalClientsWithPassport() {
    return jpaRepository.countByPassportPassportNumberIsNotNull();
  }

  @Override
  public Long getTotalClientsWithoutPassport() {
    return jpaRepository.countByPassportPassportNumberIsNull();
  }

  @Override
  public List<Client> getClientsThatNeedToRenewPassport() {
    LocalDate oneYearFromNow = LocalDate.now().plusMonths(6);

    List<ClientModel> clientsNeedingRenewal =
        jpaRepository.findByPassportPassportNumberIsNotNullAndPassportExpirationDateLessThan(
        oneYearFromNow);

    return clientsNeedingRenewal.stream()
        .map(clientMapper::toDomain)
        .collect(Collectors.toList());
  }

}

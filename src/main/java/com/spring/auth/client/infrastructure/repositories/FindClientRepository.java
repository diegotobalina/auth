package com.spring.auth.client.infrastructure.repositories;

import com.spring.auth.client.infrastructure.repositories.ports.FindClientPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.client.domain.ClientJpa;
import com.spring.auth.client.domain.ClientMapper;
import com.spring.auth.client.infrastructure.repositories.jpa.ClientRepositoryJpa;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.util.SearchUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FindClientRepository implements FindClientPort {

  private ClientRepositoryJpa clientRepositoryJpa;

  @Override
  public Page<Client> search(String id, String clientId, int page, int size) {
    ClientJpa clientJpa = new ClientJpa();
    clientJpa.setId(id);
    clientJpa.setClientId(clientId);

    List<String> wantedFields = List.of("id", "clientId");

    Example<ClientJpa> example =
        (Example<ClientJpa>) SearchUtil.getExample(clientJpa, wantedFields);
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<ClientJpa> pagedResult = clientRepositoryJpa.findAll(example, pageRequest);
    return new PageImpl<>(
        ClientMapper.parseClientJpaList(pagedResult.getContent()),
        pageRequest,
        pagedResult.getTotalElements());
  }

  @Override
  public Client findById(String clientId) throws NotFoundException {
    Optional<ClientJpa> optional = clientRepositoryJpa.findById(clientId);
    ClientJpa clientJpa =
        optional.orElseThrow(() -> new NotFoundException("client not found by id: " + clientId));
    return ClientMapper.parse(clientJpa);
  }

  @Override
  public Client findByClientId(String clientId) throws NotFoundException {
    Optional<ClientJpa> optional = clientRepositoryJpa.findByClientId(clientId);
    ClientJpa clientJpa =
        optional.orElseThrow(() -> new NotFoundException("client not found by id: " + clientId));
    return ClientMapper.parse(clientJpa);
  }

  @Override
  public List<Client> findAll() {
    List<ClientJpa> all = clientRepositoryJpa.findAll();
    return all.stream().map(ClientMapper::parse).collect(Collectors.toList());
  }
}

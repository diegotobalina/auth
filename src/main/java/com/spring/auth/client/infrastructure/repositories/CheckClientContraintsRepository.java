package com.spring.auth.client.infrastructure.repositories;

import com.spring.auth.client.application.ports.out.CheckClientsConstraintsPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.client.infrastructure.repositories.jpa.ClientRepositoryJpa;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class CheckClientContraintsRepository implements CheckClientsConstraintsPort {

  private ClientRepositoryJpa clientRepositoryJpa;

  private List<String> getClientClientIds(List<Client> clients) {
    return clients.stream().map(Client::getClientId).collect(Collectors.toList());
  }

  private List<String> getClientIds(List<Client> clients) {
    return clients.stream().map(Client::getId).collect(Collectors.toList());
  }

  private boolean isClientIdDuplicated(List<String> clientIds, List<String> ids) {
    return clientRepositoryJpa.existsByClientIdInAndIdNotIn(clientIds, ids);
  }

  @Override
  public void check(List<Client> clients) throws DuplicatedKeyException {
    List<String> clientIds = getClientIds(clients);
    List<String> clientClientIds = getClientClientIds(clients);
    if (isClientIdDuplicated(clientIds, clientClientIds)) {
      throw new DuplicatedKeyException("duplicated value in: " + clientClientIds);
    }
  }
}

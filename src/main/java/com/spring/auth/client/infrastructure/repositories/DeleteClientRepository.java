package com.spring.auth.client.infrastructure.repositories;

import com.spring.auth.client.infrastructure.repositories.ports.DeleteClientPort;
import com.spring.auth.client.infrastructure.repositories.ports.FindClientPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.client.infrastructure.repositories.jpa.ClientRepositoryJpa;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DeleteClientRepository implements DeleteClientPort {

  private ClientRepositoryJpa clientRepositoryJpa;
  private FindClientPort findClientPort;

  @Override
  public Client delete(String clientId) throws DuplicatedKeyException, NotFoundException {
    Client client = findClientPort.findById(clientId);
    clientRepositoryJpa.deleteById(clientId);
    return client;
  }
}

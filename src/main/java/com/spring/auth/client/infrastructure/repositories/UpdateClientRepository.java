package com.spring.auth.client.infrastructure.repositories;

import com.spring.auth.client.application.ports.out.CheckClientsConstraintsPort;
import com.spring.auth.client.application.ports.out.UpdateClientPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.client.domain.ClientJpa;
import com.spring.auth.client.domain.ClientMapper;
import com.spring.auth.client.infrastructure.repositories.jpa.ClientRepositoryJpa;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UpdateClientRepository implements UpdateClientPort {

  private ClientRepositoryJpa clientRepositoryJpa;
  private CheckClientsConstraintsPort checkClientsConstraintsPort;

  @Override
  public Client update(Client client) throws DuplicatedKeyException {
    checkClientsConstraintsPort.check(List.of(client));
    return getFirstFromList(saveClients(List.of(client)));
  }

  private Client getFirstFromList(List<Client> clients) {
    return clients.stream().findFirst().get();
  }

  private List<Client> saveClients(List<Client> clients) {
    List<ClientJpa> clientsJpa = ClientMapper.parseClientList(clients);
    List<ClientJpa> savedClientsJpa = clientRepositoryJpa.saveAll(clientsJpa);
    return ClientMapper.parseClientJpaList(savedClientsJpa);
  }
}

package com.spring.auth.client.infrastructure.repositories;

import com.spring.auth.client.infrastructure.repositories.ports.CheckClientsConstraintsPort;
import com.spring.auth.client.infrastructure.repositories.ports.CreateClientPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.client.domain.ClientJpa;
import com.spring.auth.client.domain.ClientMapper;
import com.spring.auth.client.infrastructure.repositories.jpa.ClientRepositoryJpa;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.shared.application.port.EncryptionPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class CreateClientRepository implements CreateClientPort {

  private ClientRepositoryJpa clientRepositoryJpa;
  private CheckClientsConstraintsPort checkClientsConstraintsPort;
  private EncryptionPort encryptionPort;

  @Override
  public Client create(Client client) throws DuplicatedKeyException {
    checkClientsConstraintsPort.check(List.of(client));
    ClientJpa clientJpa = ClientMapper.parse(client);
    ClientJpa savedClientJpa = clientRepositoryJpa.save(clientJpa);
    return ClientMapper.parse(savedClientJpa);
  }

  @Override
  public Client create() throws DuplicatedKeyException {
    String clientId = System.currentTimeMillis() + UUID.randomUUID().toString();
    String clientSecret = System.currentTimeMillis() + UUID.randomUUID().toString();
    Date creationDate = new Date();
    List<String> allowedUrls = new ArrayList<>();
    List<String> allowedCallbackUrls = new ArrayList<>();
    String googleClientId = "";
    Client client =
        new Client(
            null,
            clientId,
            clientSecret,
            creationDate,
            allowedUrls,
            allowedCallbackUrls,
            (long) (1000 * 60 * 20),
            googleClientId);
    return create(client);
  }
}

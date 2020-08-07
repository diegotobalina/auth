package com.spring.auth.client.domain;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ClientMapper {

  public static ClientJpa parse(Client client) {
    final Date createdAt = client.getCreatedAt();
    final Date lastModified = client.getLastModified();
    final String createdBy = client.getCreatedBy();
    final String lastModifiedBy = client.getLastModifiedBy();
    String id = client.getId();
    String clientId = client.getClientId();
    String clientSecret = client.getClientSecret();
    Date creationDate = client.getCreationDate();
    List<String> allowedUrls = client.getAllowedUrls();
    List<String> allowedCallbackUrls = client.getAllowedCallbackUrls();
    long expirationTokenToken = client.getExpirationTimeToken();
    String googleClientId = client.getGoogleClientId();
    return new ClientJpa(
        createdAt,
        lastModified,
        createdBy,
        lastModifiedBy,
        id,
        clientId,
        clientSecret,
        creationDate,
        allowedUrls,
        allowedCallbackUrls,
        expirationTokenToken,
        googleClientId);
  }

  public static Client parse(ClientJpa clientJpa) {
    final Date createdAt = clientJpa.getCreatedAt();
    final Date lastModified = clientJpa.getLastModified();
    final String createdBy = clientJpa.getCreatedBy();
    final String lastModifiedBy = clientJpa.getLastModifiedBy();
    String id = clientJpa.getId();
    String clientId = clientJpa.getClientId();
    String clientSecret = clientJpa.getClientSecret();
    Date creationDate = clientJpa.getCreationDate();
    List<String> allowedUrls = clientJpa.getAllowedUrls();
    List<String> allowedCallbackUrls = clientJpa.getAllowedCallbackUrls();
    long expirationTokenToken = clientJpa.getExpirationTimeToken();
    String googleClientId = clientJpa.getGoogleClientId();
    return new Client(
        createdAt,
        lastModified,
        createdBy,
        lastModifiedBy,
        id,
        clientId,
        clientSecret,
        creationDate,
        allowedUrls,
        allowedCallbackUrls,
        expirationTokenToken,
        googleClientId);
  }

  public static List<Client> parseClientJpaList(List<ClientJpa> clientJpas) {
    return clientJpas.stream().map(ClientMapper::parse).collect(Collectors.toList());
  }

  public static List<ClientJpa> parseClientList(List<Client> clients) {
    return clients.stream().map(ClientMapper::parse).collect(Collectors.toList());
  }
}

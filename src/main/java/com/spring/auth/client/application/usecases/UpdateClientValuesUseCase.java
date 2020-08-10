package com.spring.auth.client.application.usecases;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.client.application.ports.UpdateClientValues;
import com.spring.auth.client.infrastructure.repositories.ports.FindClientPort;
import com.spring.auth.client.infrastructure.repositories.ports.UpdateClientPort;
import com.spring.auth.client.domain.Client;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import lombok.AllArgsConstructor;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class UpdateClientValuesUseCase implements UpdateClientValues {

  private FindClientPort findClientPort;
  private UpdateClientPort updateClientPort;

  @Override
  public Client update(
      String clientId,
      List<String> allowedUrls,
      List<String> allowedCallbackUrls,
      long expirationTokenTime,
      String googleClientId)
      throws DuplicatedKeyException, NotFoundException {
    Client client = findClientPort.findById(clientId);
    client.setAllowedUrls(allowedUrls);
    client.setAllowedCallbackUrls(allowedCallbackUrls);
    client.setExpirationTimeToken(expirationTokenTime);
    client.setGoogleClientId(googleClientId);
    return updateClientPort.update(client);
  }
}

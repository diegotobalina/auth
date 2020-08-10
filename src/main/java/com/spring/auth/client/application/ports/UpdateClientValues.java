package com.spring.auth.client.application.ports;

import com.spring.auth.client.domain.Client;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface UpdateClientValues {
  Client update(
      String clientId,
      List<String> allowedUrls,
      List<String> allowedCallbackUrls,
      long expirationTokenTime,
      String googleClientId)
      throws DuplicatedKeyException, NotFoundException;
}

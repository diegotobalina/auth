package com.spring.auth.client.infrastructure.repositories.ports;

import com.spring.auth.client.domain.Client;
import com.spring.auth.exceptions.application.DuplicatedKeyException;

import java.util.List;

public interface CheckClientsConstraintsPort {
  void check(List<Client> clients) throws DuplicatedKeyException;
}

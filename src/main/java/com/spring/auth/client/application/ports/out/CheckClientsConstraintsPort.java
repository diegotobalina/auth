package com.spring.auth.client.application.ports.out;

import com.spring.auth.client.domain.Client;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;

import java.util.List;

public interface CheckClientsConstraintsPort {
  void check(List<Client> clients) throws DuplicatedKeyException;
}

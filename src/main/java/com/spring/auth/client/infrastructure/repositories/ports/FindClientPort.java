package com.spring.auth.client.infrastructure.repositories.ports;

import com.spring.auth.client.domain.Client;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface FindClientPort {
  Page<Client> search(String id,String clientId, int page, int size);

  Client findById(String id) throws DuplicatedKeyException, NotFoundException;

  Client findByClientId(String clientId) throws NotFoundException;

  List<Client> findAll();
}

package com.spring.auth.client.application.ports.out;

import com.spring.auth.client.domain.Client;
import com.spring.auth.exceptions.application.DuplicatedKeyException;

/** @author diegotobalina created on 24/06/2020 */
public interface UpdateClientPort {
  Client update(Client client) throws DuplicatedKeyException;
}

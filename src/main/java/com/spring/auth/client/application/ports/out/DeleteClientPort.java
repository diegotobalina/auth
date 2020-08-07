package com.spring.auth.client.application.ports.out;

import com.spring.auth.client.domain.Client;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;

/** @author diegotobalina created on 24/06/2020 */
public interface DeleteClientPort {
  Client delete(String clientId) throws DuplicatedKeyException, NotFoundException;
}

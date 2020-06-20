package com.spring.auth.session.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.domain.Session;

public interface CreateSessionPort {
  Session create(Session session) throws DuplicatedKeyException, NotFoundException;
}

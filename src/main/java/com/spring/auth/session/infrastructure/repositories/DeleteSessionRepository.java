package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.session.application.ports.out.DeleteSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.security.InvalidParameterException;
import java.util.Objects;

@Repository
@AllArgsConstructor
public class DeleteSessionRepository implements DeleteSessionPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public void delete(final Session session) {
    if (Objects.isNull(session)) throw new InvalidParameterException("session must not be null");
    if (Objects.isNull(session.getId()))
      throw new InvalidParameterException("session id must not be null");
    if (Objects.isNull(session.getToken()))
      throw new InvalidParameterException("session token must not be null");
    this.sessionRepositoryJpa.deleteById(session.getId());
  }
}

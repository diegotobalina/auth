package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.out.FindSessionByTokenPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.domain.SessionJpa;
import com.spring.auth.session.domain.SessionMapper;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class FindSessionByTokenRepository implements FindSessionByTokenPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public Session find(final String value) throws NotFoundException {
    final Optional<SessionJpa> optional = sessionRepositoryJpa.findByToken(value);
    if (optional.isEmpty()) {
      throw new NotFoundException("session not found with value: " + value);
    }
    final SessionJpa sessionJpa = optional.get();
    return SessionMapper.parse(sessionJpa);
  }
}

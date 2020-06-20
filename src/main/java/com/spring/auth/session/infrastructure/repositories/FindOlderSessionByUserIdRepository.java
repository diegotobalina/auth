package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.out.FindOlderSessionByUserIdPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.domain.SessionJpa;
import com.spring.auth.session.domain.SessionMapper;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class FindOlderSessionByUserIdRepository implements FindOlderSessionByUserIdPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public Session find(String userId) throws NotFoundException {
    Optional<SessionJpa> optionalSessionJpa =
        sessionRepositoryJpa.findFirstByUserIdOrderByExpiration(userId);
    if (optionalSessionJpa.isEmpty()) {
      throw new NotFoundException("there are not sessions for the user: " + userId);
    }
    return SessionMapper.parse(optionalSessionJpa.get());
  }
}

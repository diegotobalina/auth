package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.out.FindSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.domain.SessionJpa;
import com.spring.auth.session.domain.SessionMapper;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class FindSessionRepository implements FindSessionPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public List<Session> findAll() {
    List<SessionJpa> sessionJpaList = sessionRepositoryJpa.findAll();
    return SessionMapper.parseSessionJpaList(sessionJpaList);
  }

  @Override
  public List<Session> findAllByUserId(String userId) {
    List<SessionJpa> sessionJpaList = sessionRepositoryJpa.findAllByUserId(userId);
    return SessionMapper.parseSessionJpaList(sessionJpaList);
  }

  @Override
  public List<Session> findAllByExpirationBefore(Date date) {
    List<SessionJpa> sessionJpaList = sessionRepositoryJpa.findAllByExpirationBefore(date);
    return SessionMapper.parseSessionJpaList(sessionJpaList);
  }

  @Override
  public Session findByToken(String token) throws NotFoundException {
    Optional<SessionJpa> optional = sessionRepositoryJpa.findByToken(token);
    SessionJpa sessionJpa =
        optional.orElseThrow(() -> new NotFoundException("session not found with token: " + token));
    return SessionMapper.parse(sessionJpa);
  }

  @Override
  public Session findOlderByUserId(String userId) throws NotFoundException {
    Optional<SessionJpa> optional = sessionRepositoryJpa.findFirstByUserIdOrderByExpiration(userId);
    SessionJpa sessionJpa =
        optional.orElseThrow(
            () -> new NotFoundException("older session not found with userId: " + userId));
    return SessionMapper.parse(sessionJpa);
  }
}

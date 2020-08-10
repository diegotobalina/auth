package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.session.infrastructure.repositories.ports.RefreshSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.domain.SessionJpa;
import com.spring.auth.session.domain.SessionMapper;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class RefreshSessionRepository implements RefreshSessionPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public void refresh(Session session) {
    session.refresh(); // todo: check possible session modifications bug
    saveSession(session);
  }

  private void saveSession(Session session) {
    SessionJpa sessionJpa = SessionMapper.parse(session);
    SessionJpa sessionJpaRefreshed = sessionRepositoryJpa.save(sessionJpa);
    SessionMapper.parse(sessionJpaRefreshed);
  }
}

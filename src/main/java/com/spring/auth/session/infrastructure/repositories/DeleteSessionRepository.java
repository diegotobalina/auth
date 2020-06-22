package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.session.application.ports.out.DeleteSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DeleteSessionRepository implements DeleteSessionPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public void delete(final Session session) {
    this.sessionRepositoryJpa.deleteById(session.getId());
  }
}

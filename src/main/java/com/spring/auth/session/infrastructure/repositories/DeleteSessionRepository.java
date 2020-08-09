package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.out.DeleteSessionPort;
import com.spring.auth.session.application.ports.out.FindSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class DeleteSessionRepository implements DeleteSessionPort {

  private SessionRepositoryJpa sessionRepositoryJpa;
  private FindSessionPort findSessionPort;

  @Override
  public void delete(final Session session) {
    this.sessionRepositoryJpa.deleteById(session.getId());
  }

  @Override
  public void delete(String token) throws NotFoundException {
    Session session = findSessionPort.findByToken(token);
    this.delete(session);
  }
}

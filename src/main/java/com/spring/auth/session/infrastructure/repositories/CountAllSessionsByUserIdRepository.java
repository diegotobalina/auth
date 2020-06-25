package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.session.application.ports.out.CountAllSessionsByUserIdPort;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class CountAllSessionsByUserIdRepository implements CountAllSessionsByUserIdPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public int countAll(String userId) {
    return sessionRepositoryJpa.countAllByUserId(userId);
  }
}

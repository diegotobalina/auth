package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.session.application.ports.out.CountAllSessionsByUserIdPort;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CountAllSessionsByUserIdRepository implements CountAllSessionsByUserIdPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public int countAll(String userId) {
    return sessionRepositoryJpa.countAllByUserId(userId);
  }
}

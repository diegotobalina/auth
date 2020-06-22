package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.session.application.ports.out.FindAllSessionsByUserIdPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.domain.SessionJpa;
import com.spring.auth.session.domain.SessionMapper;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FindAllSessionsByUserIdRepository implements FindAllSessionsByUserIdPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public List<Session> findAll(String userId) {
    List<SessionJpa> sessionJpas = sessionRepositoryJpa.findAllByUserId(userId);
    return sessionJpas.stream().map(SessionMapper::parse).collect(Collectors.toList());
  }
}

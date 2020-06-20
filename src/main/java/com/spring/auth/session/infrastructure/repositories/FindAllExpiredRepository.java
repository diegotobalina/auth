package com.spring.auth.session.infrastructure.repositories;

import com.spring.auth.session.application.ports.out.FindAllExpiredSessionsPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.domain.SessionJpa;
import com.spring.auth.session.domain.SessionMapper;
import com.spring.auth.session.infrastructure.repositories.jpa.SessionRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FindAllExpiredRepository implements FindAllExpiredSessionsPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public List<Session> findAll() {
    Date currentTime = new Date();
    List<SessionJpa> allByExpirationBefore =
        sessionRepositoryJpa.findAllByExpirationBefore(currentTime);
    return allByExpirationBefore.stream().map(SessionMapper::parse).collect(Collectors.toList());
  }
}

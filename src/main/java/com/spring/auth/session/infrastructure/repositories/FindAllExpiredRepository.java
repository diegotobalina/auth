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

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class FindAllExpiredRepository implements FindAllExpiredSessionsPort {

  private SessionRepositoryJpa sessionRepositoryJpa;

  @Override
  public List<Session> findAll() {
    Date now = new Date();
    List<SessionJpa> allByExpirationBefore = sessionRepositoryJpa.findAllByExpirationBefore(now);
    return allByExpirationBefore.stream().map(SessionMapper::parse).collect(Collectors.toList());
  }
}

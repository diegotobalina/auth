package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.session.application.ports.out.FindSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.user.application.ports.out.LoadUserSessionsPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class LoadUserSessionsRepository implements LoadUserSessionsPort {

  private FindSessionPort findSessionPort;

  @Override
  public User load(User user) {
    String userId = user.getId();
    List<Session> sessionsByUserId = findSessionPort.findAllByUserId(userId);
    user.setSessions(sessionsByUserId);
    return user;
  }
}

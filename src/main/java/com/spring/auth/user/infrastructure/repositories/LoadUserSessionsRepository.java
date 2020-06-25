package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.session.application.ports.out.FindAllSessionsByUserIdPort;
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

  private FindAllSessionsByUserIdPort findAllSessionsByUserIdPort;

  /**
   * Load the user sessions from the database
   *
   * @param user User that will receive the session list
   * @return User with the sessions list
   */
  @Override
  public User load(User user) {
    String id = user.getId();
    List<Session> allByUserId = findAllSessionsByUserIdPort.findAll(id);
    user.setSessions(allByUserId);
    return user;
  }
}

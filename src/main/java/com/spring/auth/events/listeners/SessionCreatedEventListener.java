package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.SessionCreatedEvent;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.in.DeleteOlderSessionByUserIdPort;
import com.spring.auth.session.application.ports.out.CountSessionPort;
import com.spring.auth.session.domain.Session;
import com.spring.auth.user.application.ports.out.FindUserPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class SessionCreatedEventListener {

  private FindUserPort findUserPort;
  private DeleteOlderSessionByUserIdPort deleteOlderSessionByUserIdPort;
  private CountSessionPort countSessionPort;

  @Async
  @TransactionalEventListener
  public void sessionCreated(SessionCreatedEvent sessionCreatedEvent) throws NotFoundException {
    Session createdSession = sessionCreatedEvent.getSource();
    checkMaxUserSessions(createdSession);
  }

  private void checkMaxUserSessions(Session session) throws NotFoundException {
    // if the user can´t have more open sessions the oldest one will be deleted
    String userId = session.getUserId();
    if (!canUserHaveMoreSessions(userId)) {
      deleteOlderSessionByUserIdPort.delete(userId);
    }
  }

  private boolean canUserHaveMoreSessions(String userId) throws NotFoundException {
    User user = findUserPort.findById(userId);
    int sessionCount = countSessionPort.countAllByUserId(userId);
    return user.canHaveMoreSessions(sessionCount);
  }
}

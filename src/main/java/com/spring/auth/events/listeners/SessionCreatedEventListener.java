package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.SessionCreatedEvent;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.application.ports.RemoveIfMaxSessionsReachedPort;
import com.spring.auth.session.domain.Session;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class SessionCreatedEventListener {

  private RemoveIfMaxSessionsReachedPort removeIfMaxSessionsReachedPort;

  @Async
  @TransactionalEventListener
  public void eventListener(SessionCreatedEvent sessionCreatedEvent) throws NotFoundException {
    Session createdSession = sessionCreatedEvent.getSource();
    removeIfMaxSessionsReached(createdSession);
  }

  private void removeIfMaxSessionsReached(Session createdSession) throws NotFoundException {
    removeIfMaxSessionsReachedPort.remove(createdSession.getUserId());
  }
}

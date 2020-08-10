package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.SessionUsedEvent;
import com.spring.auth.session.domain.Session;
import com.spring.auth.session.infrastructure.repositories.ports.RefreshSessionPort;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class SessionUsedEventListener {

  private RefreshSessionPort refreshSessionPort;

  /** When a session is used expiration time should be changed */
  @Async
  @TransactionalEventListener
  public void eventListener(SessionUsedEvent sessionUsedEvent) {
    Session usedSession = sessionUsedEvent.getSource();
    refreshSession(usedSession);
  }

  private void refreshSession(Session usedSession) {
    refreshSessionPort.refresh(usedSession);
  }
}

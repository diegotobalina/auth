package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.SessionUsedEvent;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.session.application.ports.out.RefreshSessionPort;
import com.spring.auth.session.domain.Session;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class SessionUsedEventListener {

  private RefreshSessionPort refreshSessionPort;

  @Async
  @TransactionalEventListener
  public void sessionUsed(SessionUsedEvent sessionUsedEvent) throws DuplicatedKeyException {
    Session usedSession = sessionUsedEvent.getSource();
    refreshSession(usedSession);
  }

  /** When a session is used expiration time should be changed */
  private void refreshSession(Session usedSession) {
    refreshSessionPort.refresh(usedSession);
  }
}

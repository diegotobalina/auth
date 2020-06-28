package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.SessionUsedEvent;
import com.spring.auth.session.application.ports.out.RefreshSessionPort;
import com.spring.auth.session.domain.Session;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
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
  public void refreshSession(SessionUsedEvent sessionUsedEvent) {
    Session usedSession = sessionUsedEvent.getSource();
    refreshSessionPort.refresh(usedSession);
  }
}

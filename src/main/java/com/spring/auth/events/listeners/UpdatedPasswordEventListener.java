package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.UpdatedPasswordEvent;
import com.spring.auth.session.infrastructure.repositories.ports.DeleteSessionPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class UpdatedPasswordEventListener {

  private DeleteSessionPort deleteSessionPort;

  /** When user change the password all sessions should be removed */
  @Async
  @TransactionalEventListener
  public void eventListener(UpdatedPasswordEvent updatedPasswordEvent) {
    User user = updatedPasswordEvent.getSource();
    deleteSessions(user);
  }

  private void deleteSessions(User user) {
    deleteSessionPort.deleteByUserId(user.getId());
  }
}

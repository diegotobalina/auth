package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.ScopeDeletedEvent;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.RemoveScopesFromRolePort;
import com.spring.auth.scope.domain.Scope;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class ScopeDeletedEventListener {

  private RemoveScopesFromRolePort removeScopesFromRolePort;

  @Async
  @TransactionalEventListener
  public void eventListener(ScopeDeletedEvent scopeDeletedEvent) throws DuplicatedKeyException {
    Scope deletedScope = scopeDeletedEvent.getSource();
    removeScopesFromRole(deletedScope);
  }

  private void removeScopesFromRole(Scope deletedScope) throws DuplicatedKeyException {
    removeScopesFromRolePort.removeFromAll(deletedScope.getId());
  }
}

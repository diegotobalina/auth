package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.ScopeDeletedEvent;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.in.RemoveScopesFromRolePort;
import com.spring.auth.role.application.ports.out.FindRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class ScopeDeletedEventListener {

  private RemoveScopesFromRolePort removeScopesFromRolePort;
  private FindRolePort findRolePort;

  /** When a scope is deleted should be removed from the roles */
  @Async
  @TransactionalEventListener
  public void removeScopesFromRoles(ScopeDeletedEvent scopeDeletedEvent)
      throws DuplicatedKeyException {
    Scope deletedScope = scopeDeletedEvent.getSource();
    List<Role> rolesWithTheScope = findRolePort.findAllByScopeId(deletedScope.getId());
    removeScopesFromRolePort.removeFromAll(rolesWithTheScope, List.of(deletedScope.getId()));
  }
}

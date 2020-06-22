package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.ScopeDeletedEvent;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.in.RemoveScopesFromRolesPort;
import com.spring.auth.role.application.ports.out.FindAllRolesByScopeIdPort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.domain.Scope;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Collections;
import java.util.List;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class ScopeDeletedEventListener {

  private RemoveScopesFromRolesPort removeScopesFromRolesPort;
  private FindAllRolesByScopeIdPort findAllRolesByScopeIdPort;

  /** When a scope is deleted should be removed from the roles */
  @Async
  @TransactionalEventListener
  public void deletedScope(ScopeDeletedEvent scopeDeletedEvent) throws DuplicatedKeyException {
    Scope deletedScope = scopeDeletedEvent.getSource();
    removeScopesFromRoles(deletedScope);
  }

  private void removeScopesFromRoles(Scope deletedScope) throws DuplicatedKeyException {
    List<Role> rolesWithTheScope = findAllRolesByScopeIdPort.findAll(deletedScope.getId());
    removeScopesFromRolesPort.remove(
        rolesWithTheScope, Collections.singletonList(deletedScope.getId()));
  }
}

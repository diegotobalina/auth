package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.RoleDeletedEvent;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.RemoveRolesFromUserPort;
import com.spring.auth.user.infrastructure.repositories.ports.FindUserPort;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class RoleDeletedEventListener {

  private RemoveRolesFromUserPort removeRolesFromUserPort;
  private FindUserPort findUserPort;

  /** When a role is deleted should be removed from the users */
  @Async
  @TransactionalEventListener
  public void eventListener(RoleDeletedEvent roleDeletedEvent) throws DuplicatedKeyException {
    Role deletedRole = roleDeletedEvent.getSource();
    removeRolesFromUser(deletedRole);
  }

  private void removeRolesFromUser(Role deletedRole) throws DuplicatedKeyException {
    removeRolesFromUserPort.remove(deletedRole.getId());
  }
}

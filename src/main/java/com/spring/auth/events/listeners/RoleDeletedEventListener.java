package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.RoleDeletedEvent;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.in.RemoveRolesFromUsersPort;
import lombok.AllArgsConstructor;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Collections;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class RoleDeletedEventListener {

  private RemoveRolesFromUsersPort removeRolesFromUsersPort;

  /** When a role is deleted should be removed from the users */
  @TransactionalEventListener
  public void roleDeleted(RoleDeletedEvent roleDeletedEvent) throws DuplicatedKeyException {
    Role source = roleDeletedEvent.getSource();
    removeRolesFromUsersPort.remove(Collections.singletonList(source.getId()));
  }
}

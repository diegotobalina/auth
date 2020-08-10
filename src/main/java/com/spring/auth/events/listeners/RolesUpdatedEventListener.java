package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.RolesUpdatedEvent;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.UpdateUsersRolesPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class RolesUpdatedEventListener {

  private UpdateUsersRolesPort updateUsersRolesPort;

  /** When a role is updated should be updated in the users */
  @Async
  @TransactionalEventListener
  public void eventListener(RolesUpdatedEvent rolesUpdatedEvent) throws DuplicatedKeyException {
    List<Role> roles = rolesUpdatedEvent.getSource();
    updateUsersRoles(roles);
  }

  private List<User> updateUsersRoles(List<Role> roles) throws DuplicatedKeyException {
    return updateUsersRolesPort.updateUsersRoles(roles);
  }
}

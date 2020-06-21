package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.RolesUpdatedEvent;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.out.FindAllUsersByRoleIdsPort;
import com.spring.auth.user.application.ports.out.UpdateUsersRolesPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class RolesUpdatedEventListener {

  private FindAllUsersByRoleIdsPort findAllUsersByRoleIdsPort;
  private UpdateUsersRolesPort updateUsersRolesPort;

  /** When a role is updated should be updated in the users */
  @Async
  @TransactionalEventListener
  public void rolesUpdated(RolesUpdatedEvent rolesUpdatedEvent) throws DuplicatedKeyException {
    List<Role> roles = rolesUpdatedEvent.getSource();
    List<String> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
    List<User> users = findAllUsersByRoleIdsPort.findAll(roleIds);
    updateUsersRolesPort.update(users, roles);
  }
}

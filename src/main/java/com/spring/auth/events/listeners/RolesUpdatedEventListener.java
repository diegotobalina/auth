package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.RolesUpdatedEvent;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.out.FindUserPort;
import com.spring.auth.user.application.ports.out.UpdateUserPort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class RolesUpdatedEventListener {

  private FindUserPort findUserPort;
  private UpdateUserPort updateUserPort;

  /** When a role is updated should be updated in the users */
  @Async
  @TransactionalEventListener
  public void updateUsersRoles(RolesUpdatedEvent rolesUpdatedEvent) throws DuplicatedKeyException {
    List<Role> updatedRoles = rolesUpdatedEvent.getSource();
    List<String> roleIds = getRoleIds(updatedRoles);
    List<User> users = findUserPort.findAllByRoleIds(roleIds);
    for (User user : users) {
      for (Role role : updatedRoles) {
        user.updateRole(role);
      }
    }
    updateUserPort.updateAll(users);
  }

  private List<String> getRoleIds(List<Role> updatedRoles) {
    return updatedRoles.stream().map(Role::getId).collect(Collectors.toList());
  }
}

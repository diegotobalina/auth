package com.spring.auth.events.listeners;

import com.spring.auth.anotations.components.CustomEventListener;
import com.spring.auth.events.domain.RoleUpdatedEvent;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.out.FindAllUsersByRoleIdPort;
import com.spring.auth.user.application.ports.out.UpdateUsersRolePort;
import com.spring.auth.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventListener
@AllArgsConstructor
public class RoleUpdatedEventListener {

  private FindAllUsersByRoleIdPort findAllUsersByRoleIdPort;
  private UpdateUsersRolePort updateUsersRolePort;

  /** When a role is updated should be updated in the users */
  @TransactionalEventListener
  public void updatedRole(RoleUpdatedEvent roleUpdatedEvent) throws DuplicatedKeyException {
    Role role = roleUpdatedEvent.getSource();
    String roleId = role.getId();
    List<User> users = findAllUsersByRoleIdPort.findAll(roleId);
    updateUsersRolePort.update(users, role);
  }
}

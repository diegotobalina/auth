package com.spring.auth.user.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.application.ports.UpdateUsersRolesPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.repositories.ports.FindUserPort;
import com.spring.auth.user.infrastructure.repositories.ports.UpdateUserPort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 10/08/2020 */
@UseCase
@AllArgsConstructor
public class UpdateUsersRolesUseCase implements UpdateUsersRolesPort {

  private FindUserPort findUserPort;
  private UpdateUserPort updateUserPort;

  @Override
  public List<User> updateUsersRoles(List<Role> roles) throws DuplicatedKeyException {
    List<String> roleIds = getRoleIds(roles);
    List<User> users = findUserPort.findAllByRoleIds(roleIds);
    for (User user : users) {
      for (Role role : roles) {
        user.updateRole(role);
      }
    }
    return updateUserPort.updateAll(users);
  }

  private List<String> getRoleIds(List<Role> updatedRoles) {
    return updatedRoles.stream().map(Role::getId).collect(Collectors.toList());
  }
}

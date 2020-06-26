package com.spring.auth.role.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.in.RemoveScopesFromRolePort;
import com.spring.auth.role.application.ports.out.FindRolePort;
import com.spring.auth.role.application.ports.out.UpdateRolePort;
import com.spring.auth.role.domain.Role;
import lombok.AllArgsConstructor;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class RemoveScopesFromRoleUseCase implements RemoveScopesFromRolePort {

  private UpdateRolePort updateRolePort;
  private FindRolePort findRolePort;

  @Override
  public Role remove(Role role, List<String> scopeIds) throws DuplicatedKeyException {
    role.removeScopes(scopeIds);
    return updateRolePort.update(role);
  }

  @Override
  public List<Role> removeFromAll(List<Role> roles, List<String> scopeIds)
      throws DuplicatedKeyException {
    roles.forEach(role -> role.removeScopes(scopeIds));
    return updateRolePort.updateAll(roles);
  }

  @Override
  public Role remove(String roleId, List<String> scopes)
      throws DuplicatedKeyException, NotFoundException {
    Role role = findRolePort.findById(roleId);
    return remove(role, scopes);
  }
}

package com.spring.auth.role.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.AddScopesToRolePort;
import com.spring.auth.role.infrastructure.repositories.ports.FindRolePort;
import com.spring.auth.role.infrastructure.repositories.ports.UpdateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.infrastructure.repositories.ports.FindScopePort;
import com.spring.auth.scope.domain.Scope;
import lombok.AllArgsConstructor;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class AddScopesToRoleUseCase implements AddScopesToRolePort {

  private FindScopePort findScopePort;
  private FindRolePort findRolePort;
  private UpdateRolePort updateRolePort;

  @Override
  public Role add(Role role, List<String> scopeIds) throws DuplicatedKeyException {
    List<Scope> scopes = findScopePort.findAllByIds(scopeIds);
    role.addScopes(scopes);
    return updateRolePort.update(role);
  }

  @Override
  public Role add(String roleId, List<String> scopeIds)
      throws NotFoundException, DuplicatedKeyException {
    Role role = findRolePort.findById(roleId);
    return add(role, scopeIds);
  }
}

package com.spring.auth.role.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.in.AddScopesToRolePort;
import com.spring.auth.role.application.ports.out.FindRoleByIdPort;
import com.spring.auth.role.application.ports.out.UpdateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.scope.application.ports.out.FindAllScopesByIdsPort;
import com.spring.auth.scope.domain.Scope;
import lombok.AllArgsConstructor;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@UseCase
@AllArgsConstructor
public class AddScopesToRoleUseCase implements AddScopesToRolePort {

  private FindAllScopesByIdsPort findAllScopesByIdsPort;
  private FindRoleByIdPort findRoleByIdPort;
  private UpdateRolePort updateRolePort;

  @Override
  public Role add(Role role, List<String> scopeIds) throws DuplicatedKeyException {
    List<Scope> scopes = findAllScopesByIdsPort.findAll(scopeIds);
    role.addScopes(scopes);
    return updateRolePort.update(role);
  }

  @Override
  public Role add(String roleId, List<String> scopeIds)
      throws NotFoundException, DuplicatedKeyException {
    Role role = findRoleByIdPort.find(roleId);
    return add(role, scopeIds);
  }
}

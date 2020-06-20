package com.spring.auth.role.application;

import com.spring.auth.anotations.components.UseCase;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.in.RemoveScopesFromRolesPort;
import com.spring.auth.role.application.ports.out.UpdateAllRolesPort;
import com.spring.auth.role.domain.Role;
import lombok.AllArgsConstructor;

import java.util.List;

@UseCase
@AllArgsConstructor
public class RemoveScopesFromRolesUseCase implements RemoveScopesFromRolesPort {

  private UpdateAllRolesPort updateAllRolesPort;

  @Override
  public List<Role> remove(List<Role> roles, List<String> scopeIds) throws DuplicatedKeyException {
    roles.forEach(role -> role.removeScopes(scopeIds));
    return updateAllRolesPort.updateAll(roles);
  }
}

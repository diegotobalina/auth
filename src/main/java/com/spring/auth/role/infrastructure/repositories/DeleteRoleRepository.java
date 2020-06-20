package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.in.RemoveScopesFromRolePort;
import com.spring.auth.role.application.ports.out.DeleteRolePort;
import com.spring.auth.role.application.ports.out.FindRoleByIdPort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.events.ports.PublishRoleDeletedEventPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class DeleteRoleRepository implements DeleteRolePort {

  private RoleRepositoryJpa roleRepositoryJpa;
  private RemoveScopesFromRolePort removeScopesFromRolePort;
  private FindRoleByIdPort findRoleByIdPort;
  private PublishRoleDeletedEventPort publishRoleDeletedEventPort;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Role delete(Role role) throws DuplicatedKeyException {
    List<Scope> roleScopes = role.getScopes();
    List<String> scopeIds = roleScopes.stream().map(Scope::getId).collect(Collectors.toList());
    Role roleWithoutScopes = removeScopesFromRolePort.remove(role, scopeIds);
    RoleJpa roleJpa = RoleMapper.parse(roleWithoutScopes);
    roleRepositoryJpa.delete(roleJpa);
    Role deletedRole = RoleMapper.parse(roleJpa);
    publishRoleDeletedEventPort.publish(deletedRole);
    return deletedRole;
  }

  @Override
  public Role delete(String roleId) throws NotFoundException, DuplicatedKeyException {
    Role role = findRoleByIdPort.find(roleId);
    return delete(role);
  }
}

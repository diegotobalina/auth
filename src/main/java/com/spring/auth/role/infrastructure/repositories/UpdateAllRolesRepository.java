package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.out.UpdateAllRolesPort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import com.spring.auth.events.ports.PublishRolesUpdatedEventPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class UpdateAllRolesRepository implements UpdateAllRolesPort {

  private RoleRepositoryJpa roleRepositoryJpa;
  private PublishRolesUpdatedEventPort publishRolesUpdatedEventPort;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<Role> updateAll(List<Role> roles) throws DuplicatedKeyException {
    List<String> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
    List<String> roleValues = roles.stream().map(Role::getValue).collect(Collectors.toList());
    if (roleRepositoryJpa.existsByValueInAndIdNotIn(
        roleValues, roleIds)) { // todo: duplicated comprobation
      throw new DuplicatedKeyException("duplicated value in: " + roleValues);
    }
    List<RoleJpa> rolesJpa = roles.stream().map(RoleMapper::parse).collect(Collectors.toList());
    List<RoleJpa> updatedRolesJpa = roleRepositoryJpa.saveAll(rolesJpa);
    List<Role> updatedRoles =
        updatedRolesJpa.stream().map(RoleMapper::parse).collect(Collectors.toList());
    publishRolesUpdatedEventPort.publish(updatedRoles);
    return updatedRoles;
  }
}

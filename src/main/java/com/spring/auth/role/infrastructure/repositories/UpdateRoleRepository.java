package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.out.UpdateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import com.spring.auth.events.ports.PublishRoleUpdatedEventPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor
public class UpdateRoleRepository implements UpdateRolePort {

  private RoleRepositoryJpa roleRepositoryJpa;
  private PublishRoleUpdatedEventPort publishRoleUpdatedEventPort;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Role update(Role role) throws DuplicatedKeyException {
    final String value = role.getValue();
    final String roleId = role.getId();
    // role value must be unique in the database
    if (roleRepositoryJpa.existsByValueAndIdNot(value, roleId)) { // todo: duplicated comprobation
      throw new DuplicatedKeyException("duplicated value: " + value);
    }
    final RoleJpa roleJpa = RoleMapper.parse(role);
    final RoleJpa updatedRoleJpa = roleRepositoryJpa.save(roleJpa);
    final Role updatedRole = RoleMapper.parse(updatedRoleJpa);
    publishRoleUpdatedEventPort.publish(updatedRole);
    return updatedRole;
  }
}

package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.events.ports.PublishRoleUpdatedEventPort;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.out.UpdateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class UpdateRoleRepository implements UpdateRolePort {

  private RoleRepositoryJpa roleRepositoryJpa;
  private PublishRoleUpdatedEventPort publishRoleUpdatedEventPort;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Role update(Role role) throws DuplicatedKeyException {
    checkRoleConstraints(role);
    Role updatedRole = updateRole(role);
    publishRoleUpdatedEventPort.publish(updatedRole);
    return updatedRole;
  }

  private Role updateRole(Role role) {
    RoleJpa roleJpa = RoleMapper.parse(role);
    RoleJpa updatedRoleJpa = roleRepositoryJpa.save(roleJpa);
    return RoleMapper.parse(updatedRoleJpa);
  }

  private void checkRoleConstraints(Role role) throws DuplicatedKeyException {
    String value = role.getValue();
    String roleId = role.getId();
    // role value must be unique in the database
    if (roleRepositoryJpa.existsByValueAndIdNot(value, roleId)) { // todo: duplicated comprobation
      throw new DuplicatedKeyException("duplicated value: " + value);
    }
  }
}

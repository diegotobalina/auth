package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.events.ports.PublishRoleUpdatedEventPort;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.out.CheckRolesConstraintsPort;
import com.spring.auth.role.application.ports.out.UpdateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class UpdateRoleRepository implements UpdateRolePort {

  private RoleRepositoryJpa roleRepositoryJpa;
  private PublishRoleUpdatedEventPort publishRoleUpdatedEventPort;
  private CheckRolesConstraintsPort checkRolesConstraintsPort;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Role update(Role role) throws DuplicatedKeyException {
    checkRolesConstraintsPort.check(Collections.singletonList(role));
    Role updatedRole = updateRole(role);
    publishRoleUpdatedEventPort.publish(updatedRole);
    return updatedRole;
  }

  private Role updateRole(Role role) {
    RoleJpa roleJpa = RoleMapper.parse(role);
    RoleJpa updatedRoleJpa = roleRepositoryJpa.save(roleJpa);
    return RoleMapper.parse(updatedRoleJpa);
  }
}

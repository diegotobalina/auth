package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.events.ports.PublishRolesUpdatedEventPort;
import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.infrastructure.repositories.ports.CheckRolesConstraintsPort;
import com.spring.auth.role.infrastructure.repositories.ports.UpdateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class UpdateRoleRepository implements UpdateRolePort {

  private RoleRepositoryJpa roleRepositoryJpa;
  private PublishRolesUpdatedEventPort publishRolesUpdatedEventPort;
  private CheckRolesConstraintsPort checkRolesConstraintsPort;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Role update(Role role) throws DuplicatedKeyException {
    List<Role> updatedRoles = updateAll(List.of(role));
    return updatedRoles.stream().findFirst().get();
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public List<Role> updateAll(List<Role> roles) throws DuplicatedKeyException {
    checkRolesConstraintsPort.check(roles);
    List<Role> updatedRoles = saveRoles(roles);
    publishRolesUpdatedEventPort.publish(updatedRoles);
    return updatedRoles;
  }

  private List<Role> saveRoles(List<Role> roles) {
    List<RoleJpa> rolesJpa = RoleMapper.parseRoleList(roles);
    List<RoleJpa> updatedRolesJpa = roleRepositoryJpa.saveAll(rolesJpa);
    return RoleMapper.parseRoleJpaList(updatedRolesJpa);
  }
}

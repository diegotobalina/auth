package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.events.ports.PublishRoleDeletedEventPort;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.infrastructure.repositories.ports.DeleteRolePort;
import com.spring.auth.role.infrastructure.repositories.ports.FindRolePort;
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
public class DeleteRoleRepository implements DeleteRolePort {

  private RoleRepositoryJpa roleRepositoryJpa;
  private FindRolePort findRolePort;
  private PublishRoleDeletedEventPort publishRoleDeletedEventPort;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Role delete(Role role) {
    RoleJpa roleJpa = RoleMapper.parse(role);
    roleRepositoryJpa.deleteById(roleJpa.getId());
    Role deletedRole = RoleMapper.parse(roleJpa);
    publishRoleDeletedEventPort.publish(deletedRole);
    return deletedRole;
  }

  @Override
  public Role delete(String roleId) throws NotFoundException {
    Role role = findRolePort.findById(roleId);
    return delete(role);
  }
}

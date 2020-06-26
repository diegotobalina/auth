package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.out.CheckRolesConstraintsPort;
import com.spring.auth.role.application.ports.out.CreateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class CreateRoleRepository implements CreateRolePort {

  private RoleRepositoryJpa roleRepositoryJpa;
  private CheckRolesConstraintsPort checkRolesConstraintsPort;

  @Override
  public Role create(Role role) throws DuplicatedKeyException {
    checkRolesConstraintsPort.check(Collections.singletonList(role));
    final RoleJpa roleJpa = RoleMapper.parse(role);
    final RoleJpa savedRoleJpa = roleRepositoryJpa.save(roleJpa);
    return RoleMapper.parse(savedRoleJpa);
  }
}

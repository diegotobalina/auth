package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.out.CreateRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class CreateRoleRepository implements CreateRolePort {

  private RoleRepositoryJpa roleRepositoryJpa;

  @Override
  public Role create(Role role) throws DuplicatedKeyException {
    final String value = role.getValue();
    // role value must be unique in the database
    if (roleRepositoryJpa.existsByValue(value)) { // todo: duplicated comprobation
      throw new DuplicatedKeyException("duplicated value: " + value);
    }
    final RoleJpa roleJpa = RoleMapper.parse(role);
    final RoleJpa savedRoleJpa = roleRepositoryJpa.save(roleJpa);
    return RoleMapper.parse(savedRoleJpa);
  }
}

package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.out.FindRoleByIdPort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class FindRoleByIdRepository implements FindRoleByIdPort {

  private RoleRepositoryJpa roleRepositoryJpa;

  @Override
  public Role find(String id) throws NotFoundException {
    final Optional<RoleJpa> optional = roleRepositoryJpa.findById(id);
    if (optional.isEmpty()) { // role not found by value
      throw new NotFoundException("role not found with id: " + id);
    }
    final RoleJpa roleJpa = optional.get();
    return RoleMapper.parse(roleJpa);
  }
}

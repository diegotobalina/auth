package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.out.FindRoleByValuePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class FindRoleByValueRepository implements FindRoleByValuePort {

  private RoleRepositoryJpa roleRepositoryJpa;

  @Override
  public Role find(String value) throws NotFoundException {
    final Optional<RoleJpa> optional = roleRepositoryJpa.findByValue(value);
    if (optional.isEmpty()) { // role not found by value
      throw new NotFoundException("role not found with value: " + value);
    }
    final RoleJpa roleJpa = optional.get();
    return RoleMapper.parse(roleJpa);
  }
}

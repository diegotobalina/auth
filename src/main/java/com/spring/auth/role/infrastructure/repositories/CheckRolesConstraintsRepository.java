package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.application.ports.out.CheckRolesConstraintsPort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class CheckRolesConstraintsRepository implements CheckRolesConstraintsPort {

  private RoleRepositoryJpa roleRepositoryJpa;

  public void check(List<Role> roles) throws DuplicatedKeyException {
    List<String> roleIds = roles.stream().map(Role::getId).collect(Collectors.toList());
    List<String> roleValues = roles.stream().map(Role::getValue).collect(Collectors.toList());
    if (roleRepositoryJpa.existsByValueInAndIdNotIn(roleValues, roleIds)) {
      throw new DuplicatedKeyException("duplicated value in: " + roleValues);
    }
  }
}

package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.role.application.ports.out.FindAllRolesPort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class FindAllRolesRepository implements FindAllRolesPort {

  private RoleRepositoryJpa roleRepositoryJpa;

  @Override
  public List<Role> findAll() {
    List<RoleJpa> all = roleRepositoryJpa.findAll();
    return all.stream().map(RoleMapper::parse).collect(Collectors.toList());
  }
}

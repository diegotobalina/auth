package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.role.application.ports.out.FindAllRolesByIdPort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FindAllRolesByIdsRepository implements FindAllRolesByIdPort {

  private RoleRepositoryJpa roleRepositoryJpa;

  @Override
  public List<Role> findAll(List<String> roleIds) {
    List<RoleJpa> all = roleRepositoryJpa.findAllByIdIn(roleIds);
    return all.stream().map(RoleMapper::parse).collect(Collectors.toList());
  }
}

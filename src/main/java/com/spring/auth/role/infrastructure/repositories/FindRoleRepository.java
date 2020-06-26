package com.spring.auth.role.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.role.application.ports.out.FindRolePort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.role.domain.RoleJpa;
import com.spring.auth.role.domain.RoleMapper;
import com.spring.auth.role.infrastructure.repositories.jpa.RoleRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class FindRoleRepository implements FindRolePort {

  private RoleRepositoryJpa roleRepositoryJpa;

  @Override
  public List<Role> findAll() {
    List<RoleJpa> roleJpaList = roleRepositoryJpa.findAll();
    return RoleMapper.parseRoleJpaList(roleJpaList);
  }

  @Override
  public List<Role> findAllByIds(List<String> roleIds) {
    List<RoleJpa> roleJpaList = roleRepositoryJpa.findAllByIdIn(roleIds);
    return RoleMapper.parseRoleJpaList(roleJpaList);
  }

  @Override
  public List<Role> findAllByScopeId(String scopeId) {
    List<RoleJpa> roleJpaList = roleRepositoryJpa.findAllByScopesId(scopeId);
    return RoleMapper.parseRoleJpaList(roleJpaList);
  }

  @Override
  public Role findById(String roleId) throws NotFoundException {
    Optional<RoleJpa> optional = roleRepositoryJpa.findById(roleId);
    RoleJpa roleJpa =
        optional.orElseThrow(() -> new NotFoundException("role not found by id: " + roleId));
    return RoleMapper.parse(roleJpa);
  }

  @Override
  public Role findByValue(String value) throws NotFoundException {
    Optional<RoleJpa> optional = roleRepositoryJpa.findByValue(value);
    RoleJpa roleJpa =
        optional.orElseThrow(() -> new NotFoundException("role not found by value: " + value));
    return RoleMapper.parse(roleJpa);
  }
}

package com.spring.auth.role.domain;

import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
public abstract class RoleMapper {

  public static List<Role> parseRoleJpaList(List<RoleJpa> roleJpaList) {
    return roleJpaList.stream().map(RoleMapper::parse).collect(Collectors.toList());
  }

  public static List<RoleJpa> parseRoleList(List<Role> roleList) {
    return roleList.stream().map(RoleMapper::parse).collect(Collectors.toList());
  }

  public static Role parse(final RoleJpa roleJpa) {
    final Date createdAt = roleJpa.getCreatedAt();
    final Date lastModified = roleJpa.getLastModified();
    final String createdBy = roleJpa.getCreatedBy();
    final String lastModifiedBy = roleJpa.getLastModifiedBy();
    final String id = roleJpa.getId();
    final String name = roleJpa.getName();
    final String description = roleJpa.getDescription();
    final String value = roleJpa.getValue();
    final List<ScopeJpa> scopeJpa = roleJpa.getScopes();
    return new Role(
        createdAt,
        lastModified,
        createdBy,
        lastModifiedBy,
        id,
        name,
        description,
        value,
        scopeJpa.stream().map(ScopeMapper::parse).collect(Collectors.toList()));
  }

  public static RoleJpa parse(final Role role) {
    final Date createdAt = role.getCreatedAt();
    final Date lastModified = role.getLastModified();
    final String createdBy = role.getCreatedBy();
    final String lastModifiedBy = role.getLastModifiedBy();
    final String id = role.getId();
    final String name = role.getName();
    final String description = role.getDescription();
    final String value = role.getValue();
    final List<Scope> scopes = role.getScopes();
    return new RoleJpa(
        createdAt,
        lastModified,
        createdBy,
        lastModifiedBy,
        id,
        name,
        description,
        value,
        scopes.stream().map(ScopeMapper::parse).collect(Collectors.toList()));
  }
}

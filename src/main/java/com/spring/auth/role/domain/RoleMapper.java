package com.spring.auth.role.domain;

import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;

import java.util.List;
import java.util.stream.Collectors;

public abstract class RoleMapper {

  public static Role parse(final RoleJpa roleJpa) {
    final String id = roleJpa.getId();
    final String name = roleJpa.getName();
    final String description = roleJpa.getDescription();
    final String value = roleJpa.getValue();
    final List<ScopeJpa> scopeJpa = roleJpa.getScopes();
    return new Role(
        id,
        name,
        description,
        value,
        scopeJpa.stream().map(ScopeMapper::parse).collect(Collectors.toList()));
  }

  public static RoleJpa parse(final Role role) {
    final String id = role.getId();
    final String name = role.getName();
    final String description = role.getDescription();
    final String value = role.getValue();
    final List<Scope> scopes = role.getScopes();
    return new RoleJpa(
        id,
        name,
        description,
        value,
        scopes.stream().map(ScopeMapper::parse).collect(Collectors.toList()));
  }
}

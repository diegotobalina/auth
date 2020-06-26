package com.spring.auth.scope.domain;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
public abstract class ScopeMapper {

  public static Scope parse(final ScopeJpa scopeJpa) {
    final String id = scopeJpa.getId();
    final String name = scopeJpa.getName();
    final String description = scopeJpa.getDescription();
    final String value = scopeJpa.getValue();
    return new Scope(id, name, description, value);
  }

  public static ScopeJpa parse(final Scope scope) {
    final String id = scope.getId();
    final String name = scope.getName();
    final String description = scope.getDescription();
    final String value = scope.getValue();
    return new ScopeJpa(id, name, description, value);
  }

  public static List<Scope> parseScopeJpaList(List<ScopeJpa> scopeJpaList) {
    return scopeJpaList.stream()
        .map(scopeJpa -> ScopeMapper.parse(scopeJpa))
        .collect(Collectors.toList());
  }

  public static List<ScopeJpa> parseScopeList(List<Scope> scopeList) {
    return scopeList.stream().map(scope -> ScopeMapper.parse(scope)).collect(Collectors.toList());
  }
}

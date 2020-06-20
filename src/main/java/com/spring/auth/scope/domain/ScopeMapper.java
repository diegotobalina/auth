package com.spring.auth.scope.domain;

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
}

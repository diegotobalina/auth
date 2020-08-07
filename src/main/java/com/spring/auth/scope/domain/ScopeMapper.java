package com.spring.auth.scope.domain;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
public abstract class ScopeMapper {

  public static Scope parse(final ScopeJpa scopeJpa) {
    final Date createdAt = scopeJpa.getCreatedAt();
    final Date lastModified = scopeJpa.getLastModified();
    final String createdBy = scopeJpa.getCreatedBy();
    final String lastModifiedBy = scopeJpa.getLastModifiedBy();
    final String id = scopeJpa.getId();
    final String name = scopeJpa.getName();
    final String description = scopeJpa.getDescription();
    final String value = scopeJpa.getValue();
    return new Scope(
        createdAt, lastModified, createdBy, lastModifiedBy, id, name, description, value);
  }

  public static ScopeJpa parse(final Scope scope) {
    final Date createdAt = scope.getCreatedAt();
    final Date lastModified = scope.getLastModified();
    final String createdBy = scope.getCreatedBy();
    final String lastModifiedBy = scope.getLastModifiedBy();
    final String id = scope.getId();
    final String name = scope.getName();
    final String description = scope.getDescription();
    final String value = scope.getValue();
    return new ScopeJpa(
        createdAt, lastModified, createdBy, lastModifiedBy, id, name, description, value);
  }

  public static List<Scope> parseScopeJpaList(List<ScopeJpa> scopeJpaList) {
    return scopeJpaList.stream().map(ScopeMapper::parse).collect(Collectors.toList());
  }

  public static List<ScopeJpa> parseScopeList(List<Scope> scopeList) {
    return scopeList.stream().map(ScopeMapper::parse).collect(Collectors.toList());
  }
}

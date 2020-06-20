package com.spring.auth.scope.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.scope.application.ports.out.CreateScopePort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;
import com.spring.auth.scope.infrastructure.repositories.jpa.ScopeRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CreateScopeRepository implements CreateScopePort {

  private ScopeRepositoryJpa scopeRepositoryJpa;

  @Override
  public Scope create(final Scope scope) throws DuplicatedKeyException {
    final String value = scope.getValue();
    // scope value must be unique in the database
    if (scopeRepositoryJpa.existsByValue(value)) {
      throw new DuplicatedKeyException("duplicated value: " + value);
    }
    final ScopeJpa scopeJpa = ScopeMapper.parse(scope);
    final ScopeJpa savedScopeJpa = scopeRepositoryJpa.save(scopeJpa);
    return ScopeMapper.parse(savedScopeJpa);
  }
}

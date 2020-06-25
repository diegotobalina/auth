package com.spring.auth.scope.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.scope.application.ports.out.CreateScopePort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;
import com.spring.auth.scope.infrastructure.repositories.jpa.ScopeRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class CreateScopeRepository implements CreateScopePort {

  private ScopeRepositoryJpa scopeRepositoryJpa;

  @Override
  public Scope create(Scope scope) throws DuplicatedKeyException {
    checkScopeConstraints(scope);
    ScopeJpa savedScopeJpa = saveScope(scope);
    return ScopeMapper.parse(savedScopeJpa);
  }

  private ScopeJpa saveScope(Scope scope) {
    ScopeJpa scopeJpa = ScopeMapper.parse(scope);
    return scopeRepositoryJpa.save(scopeJpa);
  }

  private void checkScopeConstraints(Scope scope) throws DuplicatedKeyException {
    String value = scope.getValue();
    // scope value must be unique in the database
    if (scopeRepositoryJpa.existsByValue(value)) {
      throw new DuplicatedKeyException("duplicated value: " + value);
    }
  }
}

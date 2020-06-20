package com.spring.auth.scope.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.scope.application.ports.out.FindScopeByIdPort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;
import com.spring.auth.scope.infrastructure.repositories.jpa.ScopeRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class FindScopeByIdRepository implements FindScopeByIdPort {

  private ScopeRepositoryJpa scopeRepositoryJpa;

  @Override
  public Scope find(final String scopeId) throws NotFoundException {
    final Optional<ScopeJpa> optional = scopeRepositoryJpa.findById(scopeId);
    if (optional.isEmpty()) { // scope not found by value
      throw new NotFoundException("scope not found with scopeId: " + scopeId);
    }
    final ScopeJpa scopeJpa = optional.get();
    return ScopeMapper.parse(scopeJpa);
  }
}

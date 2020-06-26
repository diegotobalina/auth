package com.spring.auth.scope.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.scope.application.ports.out.FindScopePort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;
import com.spring.auth.scope.infrastructure.repositories.jpa.ScopeRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class FindScopeRepository implements FindScopePort {

  private ScopeRepositoryJpa scopeRepositoryJpa;

  @Override
  public List<Scope> findAllByIds(List<String> ids) {
    List<ScopeJpa> sessionJpaList = scopeRepositoryJpa.findAllByIdIn(ids);
    return ScopeMapper.parseScopeJpaList(sessionJpaList);
  }

  @Override
  public List<Scope> findAll() {
    List<ScopeJpa> sessionJpaList = scopeRepositoryJpa.findAll();
    return ScopeMapper.parseScopeJpaList(sessionJpaList);
  }

  @Override
  public Scope findById(String scopeId) throws NotFoundException {
    Optional<ScopeJpa> optional = scopeRepositoryJpa.findById(scopeId);
    ScopeJpa scope =
        optional.orElseThrow(() -> new NotFoundException("scope not found with id: " + scopeId));
    return ScopeMapper.parse(scope);
  }

  @Override
  public Scope findByValue(String value) throws NotFoundException {
    Optional<ScopeJpa> optional = scopeRepositoryJpa.findByValue(value);
    ScopeJpa scope =
        optional.orElseThrow(() -> new NotFoundException("scope not found with value: " + value));
    return ScopeMapper.parse(scope);
  }
}

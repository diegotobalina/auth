package com.spring.auth.scope.infrastructure.repositories;

import com.spring.auth.scope.application.ports.out.FindAllScopesPort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;
import com.spring.auth.scope.infrastructure.repositories.jpa.ScopeRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FindAllScopesRepository implements FindAllScopesPort {

  private ScopeRepositoryJpa scopeRepositoryJpa;

  @Override
  public List<Scope> findAll() {
    List<ScopeJpa> all = scopeRepositoryJpa.findAll();
    return all.stream().map(ScopeMapper::parse).collect(Collectors.toList());
  }
}

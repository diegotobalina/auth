package com.spring.auth.scope.infrastructure.repositories;

import com.spring.auth.scope.application.ports.out.FindAllScopesByIdsPort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;
import com.spring.auth.scope.infrastructure.repositories.jpa.ScopeRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class FindAllScopesByIdsRepository implements FindAllScopesByIdsPort {

  private ScopeRepositoryJpa scopeRepositoryJpa;

  @Override
  public List<Scope> findAll(List<String> ids) {
    List<ScopeJpa> allById = scopeRepositoryJpa.findAllByIdIn(ids);
    return allById.stream().map(ScopeMapper::parse).collect(Collectors.toList());
  }
}

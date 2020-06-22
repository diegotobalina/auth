package com.spring.auth.scope.infrastructure.repositories;

import com.spring.auth.events.ports.PublishScopeDeletedEventPort;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.scope.application.ports.out.DeleteScopePort;
import com.spring.auth.scope.application.ports.out.FindScopeByIdPort;
import com.spring.auth.scope.domain.Scope;
import com.spring.auth.scope.domain.ScopeJpa;
import com.spring.auth.scope.domain.ScopeMapper;
import com.spring.auth.scope.infrastructure.repositories.jpa.ScopeRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@AllArgsConstructor
public class DeleteScopeRepository implements DeleteScopePort {

  private ScopeRepositoryJpa scopeRepositoryJpa;
  private FindScopeByIdPort findScopeByIdPort;
  private PublishScopeDeletedEventPort publishScopeDeletedEventPort;

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Scope delete(Scope scope) {
    Scope deletedScope = deleteScope(scope);
    publishScopeDeletedEventPort.publish(deletedScope);
    return deletedScope;
  }

  @Override
  public Scope delete(String scopeId) throws NotFoundException {
    Scope scope = findScopeByIdPort.find(scopeId);
    return delete(scope);
  }

  private Scope deleteScope(Scope scope) {
    ScopeJpa scopeJpa = ScopeMapper.parse(scope);
    scopeRepositoryJpa.delete(scopeJpa);
    return ScopeMapper.parse(scopeJpa);
  }
}

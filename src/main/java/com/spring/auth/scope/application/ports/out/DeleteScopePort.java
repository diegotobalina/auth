package com.spring.auth.scope.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.scope.domain.Scope;

public interface DeleteScopePort {
  Scope delete(Scope scope);

  Scope delete(String scopeId) throws NotFoundException;
}

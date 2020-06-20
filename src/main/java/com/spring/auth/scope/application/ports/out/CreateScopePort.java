package com.spring.auth.scope.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.scope.domain.Scope;

public interface CreateScopePort {
  Scope create(Scope scope) throws DuplicatedKeyException;
}

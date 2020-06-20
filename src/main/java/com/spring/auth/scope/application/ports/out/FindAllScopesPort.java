package com.spring.auth.scope.application.ports.out;

import com.spring.auth.scope.domain.Scope;

import java.util.List;

public interface FindAllScopesPort {
  List<Scope> findAll();
}

package com.spring.auth.scope.application.ports.out;

import com.spring.auth.scope.domain.Scope;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface FindAllScopesByIdsPort {
  List<Scope> findAll(List<String> ids);
}

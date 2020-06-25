package com.spring.auth.scope.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.scope.domain.Scope;

/** @author diegotobalina created on 24/06/2020 */
public interface FindScopeByValuePort {
  Scope find(String value) throws NotFoundException;
}

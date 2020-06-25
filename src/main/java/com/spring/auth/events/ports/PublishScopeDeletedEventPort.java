package com.spring.auth.events.ports;

import com.spring.auth.scope.domain.Scope;

/** @author diegotobalina created on 19/06/2020 */
public interface PublishScopeDeletedEventPort {
  void publish(Scope scope);
}

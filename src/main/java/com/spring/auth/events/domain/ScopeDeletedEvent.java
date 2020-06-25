package com.spring.auth.events.domain;

import com.spring.auth.scope.domain.Scope;
import org.springframework.context.ApplicationEvent;

/** @author diegotobalina created on 24/06/2020 */
public class ScopeDeletedEvent extends ApplicationEvent {
  public ScopeDeletedEvent(Scope scope) {
    super(scope);
  }

  @Override
  public Scope getSource() {
    return (Scope) this.source;
  }
}

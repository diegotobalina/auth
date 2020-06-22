package com.spring.auth.events.publishers;

import com.spring.auth.anotations.components.CustomEventPublisher;
import com.spring.auth.events.domain.ScopeDeletedEvent;
import com.spring.auth.events.ports.PublishScopeDeletedEventPort;
import com.spring.auth.scope.domain.Scope;
import org.springframework.context.ApplicationEventPublisher;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventPublisher
public class ScopeDeletedEventPublisher extends EventPublisher
    implements PublishScopeDeletedEventPort {

  public ScopeDeletedEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    super(applicationEventPublisher);
  }

  @Override
  public void publish(Scope scope) {
    ScopeDeletedEvent scopeDeletedEvent = new ScopeDeletedEvent(scope);
    this.applicationEventPublisher.publishEvent(scopeDeletedEvent);
  }
}

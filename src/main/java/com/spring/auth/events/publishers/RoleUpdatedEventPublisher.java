package com.spring.auth.events.publishers;

import com.spring.auth.anotations.components.CustomEventPublisher;
import com.spring.auth.role.domain.Role;
import com.spring.auth.events.domain.RoleUpdatedEvent;
import com.spring.auth.events.ports.PublishRoleUpdatedEventPort;
import org.springframework.context.ApplicationEventPublisher;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventPublisher
public class RoleUpdatedEventPublisher extends EventPublisher
    implements PublishRoleUpdatedEventPort {

  public RoleUpdatedEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    super(applicationEventPublisher);
  }

  @Override
  public void publish(Role role) {
    this.applicationEventPublisher.publishEvent(new RoleUpdatedEvent(role));
  }
}

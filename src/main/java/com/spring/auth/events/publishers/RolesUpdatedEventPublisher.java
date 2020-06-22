package com.spring.auth.events.publishers;

import com.spring.auth.anotations.components.CustomEventPublisher;
import com.spring.auth.role.domain.Role;
import com.spring.auth.events.domain.RolesUpdatedEvent;
import com.spring.auth.events.ports.PublishRolesUpdatedEventPort;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventPublisher
public class RolesUpdatedEventPublisher extends EventPublisher
    implements PublishRolesUpdatedEventPort {

  public RolesUpdatedEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    super(applicationEventPublisher);
  }

  @Override
  public void publish(List<Role> roles) {
    RolesUpdatedEvent rolesUpdatedEvent = new RolesUpdatedEvent(roles);
    this.applicationEventPublisher.publishEvent(rolesUpdatedEvent);
  }
}

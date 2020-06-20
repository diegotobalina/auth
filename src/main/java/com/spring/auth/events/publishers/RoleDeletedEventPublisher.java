package com.spring.auth.events.publishers;

import com.spring.auth.anotations.components.CustomEventPublisher;
import com.spring.auth.role.domain.Role;
import com.spring.auth.events.domain.RoleDeletedEvent;
import com.spring.auth.events.ports.PublishRoleDeletedEventPort;
import org.springframework.context.ApplicationEventPublisher;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventPublisher
public class RoleDeletedEventPublisher extends EventPublisher
    implements PublishRoleDeletedEventPort {

  public RoleDeletedEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    super(applicationEventPublisher);
  }

  @Override
  public void publish(Role role) {
    this.applicationEventPublisher.publishEvent(new RoleDeletedEvent(role));
  }
}

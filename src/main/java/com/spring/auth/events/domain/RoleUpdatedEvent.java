package com.spring.auth.events.domain;

import com.spring.auth.role.domain.Role;
import org.springframework.context.ApplicationEvent;

public class RoleUpdatedEvent extends ApplicationEvent {
  public RoleUpdatedEvent(Role role) {
    super(role);
  }

  @Override
  public Role getSource() {
    return (Role) this.source;
  }
}

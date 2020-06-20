package com.spring.auth.events.domain;

import com.spring.auth.role.domain.Role;
import org.springframework.context.ApplicationEvent;

import java.util.List;

public class RolesUpdatedEvent extends ApplicationEvent {
  public RolesUpdatedEvent(List<Role> roles) {
    super(roles);
  }

  @Override
  public List<Role> getSource() {
    return (List<Role>) this.source;
  }
}

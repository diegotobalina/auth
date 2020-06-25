package com.spring.auth.events.domain;

import com.spring.auth.role.domain.Role;
import org.springframework.context.ApplicationEvent;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public class RolesUpdatedEvent extends ApplicationEvent {
  public RolesUpdatedEvent(List<Role> roles) {
    super(roles);
  }

  @Override
  public List<Role> getSource() {
    return (List<Role>) this.source;
  }
}

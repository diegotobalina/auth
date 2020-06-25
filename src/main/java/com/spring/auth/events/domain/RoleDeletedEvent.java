package com.spring.auth.events.domain;

import com.spring.auth.role.domain.Role;
import org.springframework.context.ApplicationEvent;

/** @author diegotobalina created on 24/06/2020 */
public class RoleDeletedEvent extends ApplicationEvent {
  public RoleDeletedEvent(Role role) {
    super(role);
  }

  @Override
  public Role getSource() {
    return (Role) this.source;
  }
}

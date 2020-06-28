package com.spring.auth.events.domain;

import com.spring.auth.role.domain.Role;
import com.spring.auth.user.domain.User;
import org.springframework.context.ApplicationEvent;

/** @author diegotobalina created on 24/06/2020 */
public class UpdatedPasswordEvent extends ApplicationEvent {
  public UpdatedPasswordEvent(User user) {
    super(user);
  }

  @Override
  public User getSource() {
    return (User) this.source;
  }
}

package com.spring.auth.events.publishers;

import com.spring.auth.anotations.components.CustomEventPublisher;
import com.spring.auth.events.domain.RoleDeletedEvent;
import com.spring.auth.events.domain.UpdatedPasswordEvent;
import com.spring.auth.events.ports.PublishRoleDeletedEventPort;
import com.spring.auth.events.ports.PublishUpdatedPasswordEventPort;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.domain.User;
import org.springframework.context.ApplicationEventPublisher;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventPublisher
public class UpdatedPasswordEventPublisher extends EventPublisher
    implements PublishUpdatedPasswordEventPort {

  public UpdatedPasswordEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    super(applicationEventPublisher);
  }


  @Override
  public void publish(User user) {
    UpdatedPasswordEvent updatedPasswordEvent = new UpdatedPasswordEvent(user);
    this.applicationEventPublisher.publishEvent(updatedPasswordEvent);
  }
}

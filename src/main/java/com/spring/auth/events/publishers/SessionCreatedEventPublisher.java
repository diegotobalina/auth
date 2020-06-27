package com.spring.auth.events.publishers;

import com.spring.auth.anotations.components.CustomEventPublisher;
import com.spring.auth.events.domain.SessionCreatedEvent;
import com.spring.auth.events.ports.PublishSessionUsedEventPort;
import com.spring.auth.session.domain.Session;
import org.springframework.context.ApplicationEventPublisher;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventPublisher
public class SessionCreatedEventPublisher extends EventPublisher
    implements PublishSessionUsedEventPort {

  public SessionCreatedEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    super(applicationEventPublisher);
  }

  @Override
  public void publish(Session session) {
    SessionCreatedEvent sessionCreatedEvent = new SessionCreatedEvent(session);
    this.applicationEventPublisher.publishEvent(sessionCreatedEvent);
  }
}

package com.spring.auth.events.publishers;

import com.spring.auth.anotations.components.CustomEventPublisher;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;

/** @author diegotobalina created on 19/06/2020 */
@CustomEventPublisher
@AllArgsConstructor
public class EventPublisher {

  protected ApplicationEventPublisher applicationEventPublisher;
}

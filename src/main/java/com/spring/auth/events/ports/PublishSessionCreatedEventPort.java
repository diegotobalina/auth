package com.spring.auth.events.ports;

import com.spring.auth.session.domain.Session;

/** @author diegotobalina created on 19/06/2020 */
public interface PublishSessionCreatedEventPort {
  void publish(Session session);
}

package com.spring.auth.session.infrastructure.repositories.ports;

import com.spring.auth.session.domain.Session;

/** @author diegotobalina created on 24/06/2020 */
public interface RefreshSessionPort {
  void refresh(Session session);
}

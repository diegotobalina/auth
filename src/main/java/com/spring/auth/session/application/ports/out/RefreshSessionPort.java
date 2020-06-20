package com.spring.auth.session.application.ports.out;

import com.spring.auth.session.domain.Session;

public interface RefreshSessionPort {
  void refresh(Session session);
}

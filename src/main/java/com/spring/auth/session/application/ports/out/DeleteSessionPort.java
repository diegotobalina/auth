package com.spring.auth.session.application.ports.out;

import com.spring.auth.session.domain.Session;

public interface DeleteSessionPort {
  void delete(Session session);
}

package com.spring.auth.session.application.ports.in;

import com.spring.auth.exceptions.application.NotFoundException;

public interface DeleteOlderSessionByUserIdPort {
  void delete(String userId) throws NotFoundException;
}

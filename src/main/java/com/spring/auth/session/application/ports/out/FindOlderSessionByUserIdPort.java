package com.spring.auth.session.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.domain.Session;

public interface FindOlderSessionByUserIdPort {
  Session find(String userId) throws NotFoundException;
}

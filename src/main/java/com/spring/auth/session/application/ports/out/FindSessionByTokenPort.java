package com.spring.auth.session.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.session.domain.Session;

public interface FindSessionByTokenPort {
  Session find(String token) throws NotFoundException;
}

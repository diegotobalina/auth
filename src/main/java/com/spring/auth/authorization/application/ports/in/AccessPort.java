package com.spring.auth.authorization.application.ports.in;

import com.spring.auth.authorization.domain.JwtWrapper;
import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.NotFoundException;

public interface AccessPort {
  JwtWrapper access(String token) throws NotFoundException, InvalidTokenException;
}

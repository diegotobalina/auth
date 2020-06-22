package com.spring.auth.authorization.application.ports.in;

import com.spring.auth.exceptions.application.InvalidTokenException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.util.TokenUtil;

public interface AccessPort {
  TokenUtil.JwtWrapper access(String token) throws NotFoundException, InvalidTokenException;
}

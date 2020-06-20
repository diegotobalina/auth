package com.spring.auth.authorization.application.ports.in;

import com.spring.auth.exceptions.application.NotFoundException;

public interface LogoutUserPort {
  void logout(String token) throws NotFoundException;
}

package com.spring.auth.user.application.ports.in;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

/** Register e a new user in the application with the default values */
public interface RegisterUserPort {
  User register(String username, String email, String password)
      throws DuplicatedKeyException, NotFoundException;
}

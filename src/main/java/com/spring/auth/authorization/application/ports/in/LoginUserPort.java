package com.spring.auth.authorization.application.ports.in;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.WrongPasswordException;
import com.spring.auth.session.domain.Session;

public interface LoginUserPort {
  Session login(String username, String email, String password)
      throws NotFoundException, WrongPasswordException, DuplicatedKeyException;
}

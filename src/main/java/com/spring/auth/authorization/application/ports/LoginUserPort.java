package com.spring.auth.authorization.application.ports;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.LockedUserException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.WrongPasswordException;
import com.spring.auth.session.domain.Session;

/** @author diegotobalina created on 24/06/2020 */
public interface LoginUserPort {
  Session login(String username, String email, String password)
          throws NotFoundException, WrongPasswordException, DuplicatedKeyException, LockedUserException;
}

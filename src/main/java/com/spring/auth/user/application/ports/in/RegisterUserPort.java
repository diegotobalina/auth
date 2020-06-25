package com.spring.auth.user.application.ports.in;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

/** @author diegotobalina created on 24/06/2020 */
/** Register e a new user in the application with the default values */
public interface RegisterUserPort {
  User register(String username, String email, String password)
      throws DuplicatedKeyException, NotFoundException;
}

package com.spring.auth.user.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.domain.User;

/** Crate a new user in the database */
public interface CreateUserPort {
  User create(User user) throws DuplicatedKeyException;
}

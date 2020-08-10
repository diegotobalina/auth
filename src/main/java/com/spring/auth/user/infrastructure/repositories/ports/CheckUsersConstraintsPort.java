package com.spring.auth.user.infrastructure.repositories.ports;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.domain.User;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
public interface CheckUsersConstraintsPort {
  void check(User user) throws DuplicatedKeyException;

  void check(List<User> users) throws DuplicatedKeyException;
}

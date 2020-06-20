package com.spring.auth.user.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.domain.User;

import java.util.List;

/** Update all the users and save them in the database */
public interface UpdateUsersPort {
  List<User> updateAll(List<User> users) throws DuplicatedKeyException;
}

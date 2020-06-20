package com.spring.auth.user.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.domain.User;

import java.util.List;

/** Update all the roles in the user if the user have the role */
public interface UpdateUsersRolePort {
  List<User> update(List<User> users, Role role) throws DuplicatedKeyException;
}

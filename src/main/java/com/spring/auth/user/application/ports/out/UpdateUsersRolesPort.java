package com.spring.auth.user.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.role.domain.Role;
import com.spring.auth.user.domain.User;

import java.util.List;

/** Update all the roles in each user if the user have the role */
public interface UpdateUsersRolesPort {
  List<User> update(List<User> users, List<Role> roles) throws DuplicatedKeyException;
}

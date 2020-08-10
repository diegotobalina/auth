package com.spring.auth.user.application.ports;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

import java.util.List;

/** Remove the roles from a specific user */
public interface RemoveRolesFromUserPort {

  /** Remove the roles form the user */
  User remove(User user, List<String> roleIds) throws DuplicatedKeyException;
  /** Remove the roles form the user */
  User remove(String userId, List<String> roleIds) throws NotFoundException, DuplicatedKeyException;
  /** Remove the roles form all the users */
  List<User> remove(List<String> roleIds) throws DuplicatedKeyException;
  /** Remove the role form all the users */
  List<User> remove(String roleId) throws DuplicatedKeyException;
  /** Remove the roles form the users */
  List<User> remove(List<User> users, List<String> roleIds) throws DuplicatedKeyException;
}

package com.spring.auth.user.application.ports.in;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
/** Remove the roles from a specific user */
public interface RemoveRolesFromUserPort {
  User remove(User user, List<String> roleIds) throws DuplicatedKeyException;

  User remove(String userId, List<String> roleIds) throws NotFoundException, DuplicatedKeyException;

  List<User> remove(List<User> User, List<String> roleIds) throws DuplicatedKeyException;
}

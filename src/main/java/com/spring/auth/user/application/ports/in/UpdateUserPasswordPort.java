package com.spring.auth.user.application.ports.in;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.exceptions.application.WrongPasswordException;
import com.spring.auth.user.domain.User;

/** @author diegotobalina created on 24/06/2020 */
/** Update the user password */
public interface UpdateUserPasswordPort {
  User update(User user, String oldPassword, String newPassword)
      throws DuplicatedKeyException, WrongPasswordException;

  User update(User user, String newPassword) throws DuplicatedKeyException;

  void update(String userId, String oldPassword, String newPassword)
      throws NotFoundException, DuplicatedKeyException, WrongPasswordException;

  void update(String userId, String newPassword) throws NotFoundException, DuplicatedKeyException;
}

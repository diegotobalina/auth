package com.spring.auth.user.application.ports.in;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

/** @author diegotobalina created on 24/06/2020 */

/** Add roles to a specific user */
public interface UnlockUserPort {
  User unlock(String userId) throws DuplicatedKeyException, NotFoundException;

  User unlock(User user) throws DuplicatedKeyException;
}

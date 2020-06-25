package com.spring.auth.user.application.ports.out;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.domain.User;

/** @author diegotobalina created on 24/06/2020 */
/** Update the user in the database */
public interface UpdateUserPort {
  User update(User user) throws DuplicatedKeyException;
}

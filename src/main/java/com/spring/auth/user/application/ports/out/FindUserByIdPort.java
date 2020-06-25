package com.spring.auth.user.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

/** @author diegotobalina created on 24/06/2020 */
/** Find a user using the id */
public interface FindUserByIdPort {
  User find(String id) throws NotFoundException;
}

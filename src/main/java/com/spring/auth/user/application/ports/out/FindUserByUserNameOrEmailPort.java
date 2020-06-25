package com.spring.auth.user.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

/** @author diegotobalina created on 24/06/2020 */
/** Find a user using the username (first) or the email (second) */
public interface FindUserByUserNameOrEmailPort {
  User find(String username, String email) throws NotFoundException;
}

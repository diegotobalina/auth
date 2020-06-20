package com.spring.auth.user.application.ports.out;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.domain.User;

/** Find a user using the email */
public interface FindUserByEmailPort {
  User find(String email) throws NotFoundException;
}

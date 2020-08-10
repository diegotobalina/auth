package com.spring.auth.user.infrastructure.repositories.ports;

import com.spring.auth.user.domain.User;

/** @author diegotobalina created on 24/06/2020 */
public interface LoadUserSessionsPort {
  User load(User user);
}

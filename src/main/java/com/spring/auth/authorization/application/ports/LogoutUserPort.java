package com.spring.auth.authorization.application.ports;

import com.spring.auth.exceptions.application.NotFoundException;

/** @author diegotobalina created on 24/06/2020 */
public interface LogoutUserPort {
  void logout(String token) throws NotFoundException;
}

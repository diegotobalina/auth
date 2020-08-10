package com.spring.auth.session.application.ports;

import com.spring.auth.exceptions.application.NotFoundException;

/** @author diegotobalina created on 24/06/2020 */
public interface RemoveIfMaxSessionsReachedPort {
  void remove(String userId) throws NotFoundException;
}

package com.spring.auth.session.infrastructure.repositories.ports;

/** @author diegotobalina created on 24/06/2020 */
public interface CountSessionPort {
  int countAllByUserId(String userId);
}

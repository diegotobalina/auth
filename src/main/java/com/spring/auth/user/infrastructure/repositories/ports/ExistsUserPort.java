package com.spring.auth.user.infrastructure.repositories.ports;

/** @author diegotobalina created on 24/06/2020 */
public interface ExistsUserPort {
  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}

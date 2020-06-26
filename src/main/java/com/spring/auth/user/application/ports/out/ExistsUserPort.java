package com.spring.auth.user.application.ports.out;

/** @author diegotobalina created on 24/06/2020 */
public interface ExistsUserPort {
  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}

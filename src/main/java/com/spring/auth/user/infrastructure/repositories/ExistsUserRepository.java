package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.user.infrastructure.repositories.ports.ExistsUserPort;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class ExistsUserRepository implements ExistsUserPort {

  private UserRepositoryJpa userRepositoryJpa;

  @Override
  public boolean existsByUsername(String username) {
    return userRepositoryJpa.existsByUsername(username);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepositoryJpa.existsByEmail(email);
  }
}

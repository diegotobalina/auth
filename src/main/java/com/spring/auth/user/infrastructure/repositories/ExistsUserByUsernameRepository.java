package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.user.application.ports.out.ExistsUserByUserNamePort;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class ExistsUserByUsernameRepository implements ExistsUserByUserNamePort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Check if user exists in the database using the username
   *
   * @param username Username for the database query
   * @return True if the user was found, false if was not found
   */
  @Override
  public boolean exists(String username) {
    return userRepositoryJpa.existsByUsername(username);
  }
}

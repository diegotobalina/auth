package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.user.application.ports.out.ExistsUserByEmailPort;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ExistsUserByEmailRepository implements ExistsUserByEmailPort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Check if user exists in the database using the email
   *
   * @param email Username for the database query
   * @return True if the user was found, false if was not found
   */
  @Override
  public boolean exists(String email) {
    return userRepositoryJpa.existsByEmail(email);
  }
}

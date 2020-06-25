package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.out.FindUserByEmailPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class FindUserByEmailRepository implements FindUserByEmailPort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Finds a user using the email
   *
   * @param email The search will use this email
   * @return The found user
   * @throws NotFoundException When a user can not be found with that email
   */
  @Override
  public User find(final String email) throws NotFoundException {
    final Optional<UserJpa> optional = this.userRepositoryJpa.findByEmail(email);
    if (optional.isEmpty()) { // user not found by email
      throw new NotFoundException("user not found with email: " + email);
    }
    final UserJpa userJpa = optional.get();
    return UserMapper.parse(userJpa);
  }
}

package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.out.FindUserByUserNameOrEmailPort;
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
public class FindUserByUsernameOrEmailRepository implements FindUserByUserNameOrEmailPort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Find a user from the database using the username or the email
   *
   * @param username Try to find the user using the username
   * @param email If the user was not found using the username try to find it using the email
   * @return The user found
   * @throws NotFoundException When the user can not be found with the username or the email
   */
  @Override
  public User find(String username, String email) throws NotFoundException {
    // try to find user by username
    Optional<UserJpa> optionalByUsername = this.userRepositoryJpa.findByUsername(username);
    if (optionalByUsername.isPresent()) return UserMapper.parse(optionalByUsername.get());

    // try to find user by email
    Optional<UserJpa> optionalByEmail = this.userRepositoryJpa.findByEmail(email);
    if (optionalByEmail.isPresent()) return UserMapper.parse(optionalByEmail.get());

    // username not found by username or email
    throw new NotFoundException("user not found by username: " + username + ", or email: " + email);
  }
}

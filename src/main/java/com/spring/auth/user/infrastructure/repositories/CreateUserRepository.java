package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.application.ports.out.CreateUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class CreateUserRepository implements CreateUserPort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Create a new user in the database, before saving the user checks for duplicated username or *
   * duplicated email, if the user have some id throws IllegalArgumentException
   *
   * @param user User that will be created
   * @return Created user
   * @throws DuplicatedKeyException if there is already a user with same email or username in the
   *     database
   */
  @Override
  public User create(User user) throws DuplicatedKeyException {
    if (user.getId() != null) throw new IllegalArgumentException("user id must be null");
    checkUserConstraints(user);
    return saveUser(user);
  }

  private User saveUser(User user) {
    UserJpa userJpa = UserMapper.parse(user);
    UserJpa savedUser = this.userRepositoryJpa.save(userJpa);
    return UserMapper.parse(savedUser);
  }

  private void checkUserConstraints(User user) throws DuplicatedKeyException {
    // username must be unique in the database
    String username = user.getUsername();
    if (userRepositoryJpa.existsByUsername(username)) { // todo: duplicated comprobation
      throw new DuplicatedKeyException("duplicated username: " + username);
    }

    // email must be unique in the database
    String email = user.getEmail();
    if (userRepositoryJpa.existsByEmail(email)) { // todo: duplicated comprobation
      throw new DuplicatedKeyException("duplicated email: " + email);
    }
  }
}

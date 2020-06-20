package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.NotFoundException;
import com.spring.auth.user.application.ports.out.FindUserByIdPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class FindUserByIdRepository implements FindUserByIdPort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Finds an user form the database
   *
   * @param id Using the userId for the search
   * @return The user found
   * @throws NotFoundException When the user can not be found with the id
   */
  @Override
  public User find(final String id) throws NotFoundException {
    final Optional<UserJpa> optional = this.userRepositoryJpa.findById(id);
    if (optional.isEmpty()) { // user not found by id
      throw new NotFoundException("user not found with id: " + id);
    }
    final UserJpa userJpa = optional.get();
    return UserMapper.parse(userJpa);
  }
}

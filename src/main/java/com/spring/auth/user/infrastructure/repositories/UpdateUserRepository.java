package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.application.ports.out.UpdateUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UpdateUserRepository implements UpdateUserPort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Save the existing user in the database with the new data. Before save the user check for
   * duplicated username o duplicated email.
   *
   * @param user User that must be updated
   * @return List with the updated users
   * @throws DuplicatedKeyException When some constraint fails, for example duplicated username
   */
  @Override
  public User update(final User user) throws DuplicatedKeyException {

    String userId = user.getId();
    // username must be unique in the database
    final String username = user.getUsername();
    if (userRepositoryJpa.existsByUsernameAndIdNot(
        username, userId)) { // todo: duplicated comprobation
      throw new DuplicatedKeyException("duplicated username: " + username);
    }

    // email must be unique in the database
    final String email = user.getEmail();
    if (userRepositoryJpa.existsByEmailAndIdNot(email, userId)) { // todo: duplicated comprobation
      throw new DuplicatedKeyException("duplicated email: " + email);
    }

    final UserJpa userJpa = UserMapper.parse(user);
    final UserJpa savedUser = this.userRepositoryJpa.save(userJpa);
    return UserMapper.parse(savedUser);
  }
}

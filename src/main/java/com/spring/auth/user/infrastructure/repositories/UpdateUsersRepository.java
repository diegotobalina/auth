package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.application.ports.out.CheckUsersConstraintsPort;
import com.spring.auth.user.application.ports.out.UpdateUsersPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class UpdateUsersRepository implements UpdateUsersPort {

  private UserRepositoryJpa userRepositoryJpa;
  private CheckUsersConstraintsPort checkUsersConstraintsPort;

  /**
   * Save all the existing users in the database with the new data. Before save the users check for
   * duplicated usernames o duplicated emails.
   *
   * @param users Users that must be updated
   * @return List with the updated users
   * @throws DuplicatedKeyException When some constraint fails, for example duplicated username
   */
  @Override
  public List<User> updateAll(List<User> users) throws DuplicatedKeyException {
    checkUsersConstraintsPort.check(users);
    return saveUsers(users);
  }

  private List<User> saveUsers(List<User> users) {
    List<UserJpa> usersJpa = users.stream().map(UserMapper::parse).collect(Collectors.toList());
    List<UserJpa> savedUsersJpa = userRepositoryJpa.saveAll(usersJpa);
    return savedUsersJpa.stream().map(UserMapper::parse).collect(Collectors.toList());
  }
}

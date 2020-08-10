package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.infrastructure.repositories.ports.CheckUsersConstraintsPort;
import com.spring.auth.user.infrastructure.repositories.ports.UpdateUserPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class UpdateUserRepository implements UpdateUserPort {

  private UserRepositoryJpa userRepositoryJpa;
  private CheckUsersConstraintsPort checkUsersConstraintsPort;

  @Override
  public User update(User user) throws DuplicatedKeyException {
    List<User> updatedUsers = updateAll(List.of(user));
    return getFirstFromList(updatedUsers);
  }

  private User getFirstFromList(List<User> updatedUsers) {
    return updatedUsers.stream().findFirst().get();
  }

  @Override
  public List<User> updateAll(List<User> users) throws DuplicatedKeyException {
    checkUsersConstraintsPort.check(users);
    return saveUsers(users);
  }

  private List<User> saveUsers(List<User> users) {
    List<UserJpa> usersJpa = UserMapper.parseUserList(users);
    List<UserJpa> savedUsersJpa = userRepositoryJpa.saveAll(usersJpa);
    return UserMapper.parseUserJpaList(savedUsersJpa);
  }
}

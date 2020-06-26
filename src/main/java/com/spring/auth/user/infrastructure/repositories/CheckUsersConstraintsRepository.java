package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.application.ports.out.CheckUsersConstraintsPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

/** @author diegotobalina created on 24/06/2020 */
@Repository
@AllArgsConstructor
public class CheckUsersConstraintsRepository implements CheckUsersConstraintsPort {

  private UserRepositoryJpa userRepositoryJpa;

  @Override
  public void check(User user) throws DuplicatedKeyException {
    this.check(List.of(user));
  }

  @Override
  public void check(List<User> users) throws DuplicatedKeyException {
    List<String> ids = users.stream().map(User::getId).collect(Collectors.toList());
    List<String> usernames = users.stream().map(User::getUsername).collect(Collectors.toList());
    if (userRepositoryJpa.existsByUsernameInAndIdNotIn(usernames, ids))
      throw new DuplicatedKeyException("duplicated username in: " + usernames);
    List<String> emails = users.stream().map(User::getEmail).collect(Collectors.toList());
    if (userRepositoryJpa.existsByEmailInAndIdNotIn(emails, ids))
      throw new DuplicatedKeyException("duplicated email in: " + emails);
  }
}

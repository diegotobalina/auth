package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.exceptions.application.DuplicatedKeyException;
import com.spring.auth.user.infrastructure.repositories.ports.CheckUsersConstraintsPort;
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
    List<String> ids = getIds(users);
    List<String> usernames = getUsernames(users);
    if (isUsernameDuplicated(ids, usernames))
      throw new DuplicatedKeyException("duplicated username in: " + usernames);
    List<String> emails = getEmails(users);
    if (isEmailDuplicated(ids, emails))
      throw new DuplicatedKeyException("duplicated email in: " + emails);
  }

  private boolean isEmailDuplicated(List<String> ids, List<String> emails) {
    return userRepositoryJpa.existsByEmailInAndIdNotIn(emails, ids);
  }

  private boolean isUsernameDuplicated(List<String> ids, List<String> usernames) {
    return userRepositoryJpa.existsByUsernameInAndIdNotIn(usernames, ids);
  }

  private List<String> getIds(List<User> users) {
    return users.stream().map(User::getId).collect(Collectors.toList());
  }

  private List<String> getUsernames(List<User> users) {
    return users.stream().map(User::getUsername).collect(Collectors.toList());
  }

  private List<String> getEmails(List<User> users) {
    return users.stream().map(User::getEmail).collect(Collectors.toList());
  }
}

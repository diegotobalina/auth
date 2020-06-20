package com.spring.auth.user.infrastructure.repositories;

import com.spring.auth.user.application.ports.out.FindAllUsersPort;
import com.spring.auth.user.domain.User;
import com.spring.auth.user.domain.UserJpa;
import com.spring.auth.user.domain.UserMapper;
import com.spring.auth.user.infrastructure.repositories.jpa.UserRepositoryJpa;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class FindAllUsersRepository implements FindAllUsersPort {

  private UserRepositoryJpa userRepositoryJpa;

  /**
   * Search for all users in the database
   *
   * @return List with all the users
   */
  @Override
  public List<User> findAll() {
    List<UserJpa> all = this.userRepositoryJpa.findAll();
    return all.stream().map(UserMapper::parse).collect(Collectors.toList());
  }
}
